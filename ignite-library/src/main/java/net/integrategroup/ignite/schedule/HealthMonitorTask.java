package net.integrategroup.ignite.schedule;

import java.io.File;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.integrategroup.ignite.email.EmailUtils;
import net.integrategroup.ignite.persistence.model.Calendar;
import net.integrategroup.ignite.persistence.model.Health;
import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.KeyValuePair;
import net.integrategroup.ignite.persistence.service.CalendarService;
import net.integrategroup.ignite.persistence.service.HealthService;
import net.integrategroup.ignite.persistence.service.IndividualService;
import net.integrategroup.ignite.persistence.service.KeyValuePairService;
import net.integrategroup.ignite.reports.ReportEngine;
import net.integrategroup.ignite.utils.IgniteConstants;

/**
 * @author Tony De Buys
 */
@Component
public class HealthMonitorTask implements Runnable {
	private Logger logger = Logger.getLogger(HealthMonitorTask.class.getName());

	private static final String COMPONENT_FILESYSTEM      = "File System";
	private static final String COMPONENT_MAIL            = "Mail";
	private static final String COMPONENT_REPORTS         = "Reports";
	private static final String COMPONENT_SCHEDULER       = "Scheduler";
	private static final String COMPONENT_INDIVIDUAL      = "Individual";
	private static final String COMPONENT_CALENDAR        = "Calendar";

	// RAG Statuses
	private static final String RAG_STATUS_RED            = "R";
	private static final String RAG_STATUS_AMBER          = "A";
	private static final String RAG_STATUS_GREEN          = "G";

	@Autowired
	HealthService healthService;

	@Autowired
	KeyValuePairService keyValuePairService;

	@Autowired
	IndividualService individualService;

	@Autowired
	CalendarService calendarService;

	@Autowired
	ReportEngine reportEngine;

	/**
	 * The run() method is executed by the schedule task, whereas the execute() method can be executed at any time
	 *
	 */
	@Override
	public void run() {
		KeyValuePair kvp = keyValuePairService.findByKeyName(IgniteConstants.KEY_SCHEDULE_HEALTH_ENABLED);

		if (!IgniteConstants.FLAG_YES.equalsIgnoreCase(kvp.getValue())) {
			logger.info("Health Monitoring Scheduler is disabled");
			return;
		}

		execute();
	}

	/**
	 * The execute() method executes the health monitoring regardless of the KeyValuePair setting.  This can, therefore,
	 * run the tests manually.  The run() method is executed by the scheduler and will abide by the KeyValuePair setting.
	 *
	 * Run will execute several run*Checks
	 *
	 * A run*Check method should, by default, set the status to RAG_STATUS_GREEN
	 * and then, after running some tests decide if the flag should be set to AMBER or RED.
	 *
	 * It should also return a suggested action to remedy any issues
	 *
	 */
	public void execute() {
		logger.info("Health monitoring started...");

		runFileSystemCheck();
		runMailCheck();
		runReportsCheck();
		runSchedulerCheck();
		runIndividualCheck();
		runCalendarCheck();

		logger.info("Health monitor completed.");
	}

	private Health updateHealth(String componentName, String description, String suggestedAction, String ragStatus) {
		Health health = healthService.findByComponentName(componentName);

		if (health == null) {
			health = new Health();
			health.setComponentName(componentName);
		}

		health.setDescription(description);
		health.setSuggestedAction(suggestedAction);
		health.setRagStatus(ragStatus);

		// Set the username manually because this will run as a service and, therefore, won't have a username
		health.setLastUpdateUserName("HealthMonitor");

		health = healthService.save(health);

		return health;
	}

	// Source: https://stackoverflow.com/questions/3758606/how-can-i-convert-byte-size-into-a-human-readable-format-in-java
	private String humanReadableByteCountBin(long bytes) {
		long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
		if (absB < 1024) {
			return bytes + " B";
		}
		long value = absB;
		CharacterIterator ci = new StringCharacterIterator("kmgtpe");
		for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
			value >>= 10;
			ci.next();
		}
		value *= Long.signum(bytes);
		return String.format("%.1f %cb", value / 1024.0, ci.current());
	}

	private void runFileSystemCheck() {
		// get diskspace of the directory
		String reportPath = reportEngine.getReportsPath();

		String description = "Free disk space available for temporary files, reports, etc";
		String suggestedAction = "The free space could not be determined";
		String ragStatus = RAG_STATUS_RED;

		File f = new File(reportPath);
		long free = f.getFreeSpace();
		long total = f.getTotalSpace();

		if ((free == 0) && (total != 0)) {
			description = "No space left on device!";
			suggestedAction = "Free up space or request space from the administrator immediately!";
			ragStatus = RAG_STATUS_RED;
		}

		if ((free > 0) && (total > 0)) {
			int perc = (int) (((double) free / (double) total) * 100);
			description = perc + "% free (" + humanReadableByteCountBin(free) + ")";

			suggestedAction = "None";
			ragStatus = RAG_STATUS_GREEN;

			if (perc < 10) {
				suggestedAction = "Free up space or request space from the administrator";
				ragStatus = RAG_STATUS_AMBER;
			}

			if (perc < 30) {
				suggestedAction = "Monitor disk usage regularly";
				ragStatus = RAG_STATUS_GREEN;
			}
		}

		updateHealth(COMPONENT_FILESYSTEM, description, suggestedAction, ragStatus);
	}

	private void runReportsCheck() {
		int missingReports = reportEngine.getMissingReportCount();
		String suggestedAction = "None";
		String ragStatus = RAG_STATUS_GREEN;

		if (missingReports > 0) {
			suggestedAction = "Review the Setup|Reports page and remove invalid reports or upload the missing report file(s)";
			ragStatus = RAG_STATUS_RED;
		}

		updateHealth(COMPONENT_REPORTS, "Reports with missing files", suggestedAction, ragStatus);
	}

	private void runMailCheck() {
		java.util.Calendar c = java.util.Calendar.getInstance();
		int hour = c.get(java.util.Calendar.HOUR_OF_DAY);
		
		// Run this check at 05h00 only
		if (hour != 5) {
			return;
		}
		
		// mail server configuration check
		String description = "Email server configuration";
		String suggestedAction = "None";
		String ragStatus = RAG_STATUS_GREEN;

		try {
			String serverName = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SERVER_NAME);
			String serverPort = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SERVER_PORT);
			String smtpUsername = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SMTP_USERNAME);
			String smtpPassword = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SMTP_PASSWORD);

			String proxyEnabled = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_PROXY_ENABLED);
			String proxyServerName = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_PROXY_SERVER_NAME);
			String proxyPort = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_PROXY_SERVER_PORT);
			String sender = smtpUsername; // todo: configure sender name

			EmailUtils emailUtils = new EmailUtils();
			emailUtils.sendTestMail(serverName, serverPort, sender, IgniteConstants.FLAG_YES.equals(proxyEnabled),
					proxyServerName, proxyPort, smtpUsername, smtpPassword);
		} catch (Exception e) {
			description = "Invalid mail configuration: " + e.getMessage();
			suggestedAction = "Check the Setup|General|Mail configuration";
			ragStatus = RAG_STATUS_RED;

			e.printStackTrace();
		}

		updateHealth(COMPONENT_MAIL, description, suggestedAction, ragStatus);
	}

	private void runSchedulerCheck() {
		String suggestedAction = "None";
		String ragStatus = RAG_STATUS_GREEN;

		// TODO: may need to add checks for other schedulers

		// Check the Health scheduler
		KeyValuePair kvp = keyValuePairService.findByKeyName(IgniteConstants.KEY_SCHEDULE_HEALTH_ENABLED);

		if (!IgniteConstants.FLAG_YES.equalsIgnoreCase(kvp.getValue())) {
			suggestedAction = "Enable the Health Scheduler in SETUP|General|Parameters";
			ragStatus = RAG_STATUS_RED;
		}

		updateHealth(COMPONENT_SCHEDULER, "The scheduler runs health monitor jobs, system generated reports, etc", suggestedAction, ragStatus);
	}

	private void runIndividualCheck() {
		String suggestedAction = "None";
		String ragStatus = RAG_STATUS_GREEN;
		String description = "Individuals with missing Resource entries";

		List<Individual> result = individualService.getInvalidIndividuals();
		int invalidCount = (result == null) ? 0 : result.size();

		if (invalidCount > 0) {
			description = "There are " + invalidCount + " Individuals with missing Resources";
			suggestedAction = "Review the Individual table and remove/configure the invalid individuals";
			ragStatus = RAG_STATUS_RED;
		}

		updateHealth(COMPONENT_INDIVIDUAL, description, suggestedAction, ragStatus);
	}

	private void runCalendarCheck() {
		String suggestedAction = "None";
		String ragStatus = RAG_STATUS_GREEN;
		String description = "Calendar entries";

		List<Calendar> result = calendarService.findFutureEvents();
		int eventCount = (result == null) ? 0 : result.size();

		if (eventCount <= 5) {
			description = "There are only " + eventCount + " Calendar events left in the future";
			suggestedAction = "View the Setup|Calendar and add new events";
			ragStatus = RAG_STATUS_AMBER;

			if (eventCount == 0) {
				description = "There are no Calendar events left in the future";
				ragStatus = RAG_STATUS_RED;
			}
		}

		updateHealth(COMPONENT_CALENDAR, description, suggestedAction, ragStatus);
	}

}
