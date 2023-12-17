package net.integrategroup.ignite.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import net.integrategroup.ignite.filemanager.FileManager;
import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.Report;
import net.integrategroup.ignite.persistence.model.ReportParameter;
import net.integrategroup.ignite.persistence.repository.ReportRepository;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.IgniteUtils;
import net.integrategroup.ignite.utils.SecurityUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

/**
 * @author Tony De Buys
 *
 * Interface to Jaspersoft Reports to generate reports and interface with a File Manager for Reports
 *
 */
@Component
public class ReportEngine {
	private Logger logger = Logger.getLogger(ReportEngine.class.getName());

	@Value("${" + IgniteConstants.PROPERTIES_REPORTS_PATH + "}")
	private String reportsPath;

	@Value("${reports.tmp}")
	private String reportsTempPath;

	@Value("${spring.datasource.url}")
	private String springDatasourceUrl;

	@Value("${spring.datasource.username}")
	private String springDatasourceUsername;

	@Value("${spring.datasource.password}")
	private String springDatasourcePassword;

	@Value("${spring.datasource.driver-class-name}")
	private String springDatasourceDriverClassName;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ReportRepository reportRepository;

	@Autowired
	SecurityUtils securityUtils;

	public String getReportsPath() {
		return reportsPath;
	}

	public String getReportsTempPath() {
		return reportsTempPath;
	}

	public List<Report> getAllReports() {
		List<Report> reports = reportRepository.findAll();

		return reports;
	}

	public List<Report> getActiveReports() {
		// TODO: return list of reports and information from the DB
		// TODO: select * from table, check valid flag / active flag and rights

		List<Report> reports = reportRepository.findActiveAndValid();
		// TODO: for each report check if the user has the permission required to run this report

		return reports;
	}

	public String getReportUrlParameter(Map<String, Object> parameters, String key) {
		String result = null;

		for (String parameterKey: parameters.keySet()) {
			if (key.equals(parameterKey)) {
				if (parameters.get(parameterKey) != null) {
					result = (String) parameters.get(parameterKey);
				}
				break;
			}
		}

		return result;
	}

	public String runReport(Long reportId, Map<String, Object> urlParameters) throws Exception {
		// source: https://community.jaspersoft.com/questions/524537/example-java-program-call-jasper-report
		Individual user = securityUtils.getIndividual();

		String reportType = getReportUrlParameter(urlParameters, IgniteConstants.GENERATE_REPORT_AS_KEY);
		Report report = reportRepository.findByReportId(reportId);

		if (report == null) {
			throw new Exception("The report does not exist");
		}

		// Check if we have a valid report file
		if (!IgniteConstants.FLAG_YES.equalsIgnoreCase(report.getFileExistsInd())) {
			throw new Exception("The report source file does not exist");
		}

		if (!IgniteConstants.FLAG_YES.equalsIgnoreCase(report.getActiveInd())) {
			throw new Exception("The report is not active");
		}

		String permissionCodeRequired = report.getPermissionCodeRequired();

		if ((permissionCodeRequired != null) && (!permissionCodeRequired.isEmpty())) {
			if (!securityUtils.hasPermission(permissionCodeRequired)) {
				throw new Exception("The user does not have the permission required to access the report");
			}
		}

		String reportFilename = report.getReportFilename();
		String reportBinPath = reportsPath + "/bin/";

		// quietly ensure that we have a bin directory
		try {
			FileManager fm = new FileManager(getReportsPath());
			fm.mkdir(reportsPath, "/bin");
		} catch (Exception e) {
			// shhhh
		}

		String sourceFileName = reportsPath + "/" + reportFilename + IgniteConstants.JASPER_SRC_EXTENSION;
		String compiledFileName = reportBinPath + reportFilename + IgniteConstants.JASPER_BIN_EXTENSION;

		// we ensure that the filename is unique to the user so that we don't overwrite other users files if they're also executing reports
		// a background process will have to clean the directory up
		String destFilename = reportsTempPath + "/" + user.getIndividualId() + "-" + reportFilename;
		if (IgniteConstants.REPORT_TYPE_EXCEL.equals(reportType)) {
			destFilename += IgniteConstants.REPORT_TYPE_EXCEL;
		} else {
			destFilename += IgniteConstants.REPORT_TYPE_PDF;
		}

		JasperCompileManager.compileReportToFile(sourceFileName, compiledFileName);

		// Load the JDBC driver
		Class.forName(springDatasourceDriverClassName);

		// Get the connection
		try (Connection conn = DriverManager.getConnection(springDatasourceUrl,
				                                springDatasourceUsername,
				                                springDatasourcePassword)) {
			// Add standard parameters for report paths, image paths, etc
			urlParameters.put(IgniteConstants.RP_JASPER_REPORTS_PATH, IgniteUtils.getTerminatedPath(reportsPath));

			// Generate jasper print
			JasperPrint jprint = JasperFillManager.fillReport(compiledFileName, urlParameters, conn);

			if (IgniteConstants.REPORT_TYPE_EXCEL.equals(reportType)) {
				// Tips for exporting to Excel - https://community.jaspersoft.com/wiki/tips-exporting-excel
				// Based on: https://www.programcreek.com/java-api-examples/?api=net.sf.jasperreports.engine.export.JRXlsExporter
				SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
				configuration.setOnePagePerSheet(false);
				configuration.setIgnorePageMargins(true);
				configuration.setDetectCellType(true);
				configuration.setCollapseRowSpan(false);
				configuration.setRemoveEmptySpaceBetweenRows(true);

				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setExporterInput(new SimpleExporterInput(jprint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFilename));
				exporter.setConfiguration(configuration);

				exporter.exportReport();
			} else {
				// Export pdf file
				jprint.setName(report.getReportName());
				JasperExportManager.exportReportToPdfFile(jprint, destFilename);
			}
		}

		return destFilename;
	}

	public FileManager getFileManager() {
		return new FileManager(reportsPath);
	}

	public String getReportParametersHtml(Long reportId, boolean isInline) {
		Report report = reportRepository.findByReportId(reportId);
		List<ReportParameter> parameters = report.getParameters();

		int returnParameters = 0;
		boolean formOpened = false;

		String formOpeningHtml = "<form id='reportParametersForm' onsubmit='return false;'>" +
				"<fieldset>";

		String html = "<p>" +
				"<b>" + report.getReportName() + "</b><br>" +
				report.getReportDescription() +
				"</p>" +
				"<hr class='divider'>";

		int index = 0;

		if (parameters.size() > 0) {
			html = formOpeningHtml;
			formOpened = true;

			for (ReportParameter parameter: parameters) {
				returnParameters++;
				index++;

				String inputHtml = "";

				String name = parameter.getParameterName();
				String label = parameter.getParameterLabel();
				String type = parameter.getParameterType();

				switch (type) {
				case "c" :
					inputHtml = getCheckboxInput(index, parameter);
					break;
				case "n" :
					inputHtml = getNumericInput(index, parameter);
					break;
				case "d" :
					inputHtml = getDateInput(index, parameter);
					break;
				case "s" :
					inputHtml = getSelect(index, parameter);
					break;
				default :
					inputHtml = getInput(index, parameter);
					break;
				}

				html += "<div class='row'>" +
						"  <input type='hidden' id='" + getElementName(index) + "_name' name='" + getElementName(index) + "_name' value='" + name + "'>" +
						"  <label class='col-md-3' for='" + getElementName(index) + "'>" + label + "</label>" +
						"  <div class='col-md-5'>" +
						inputHtml +
						"   </div>" +
						"</div>";
			}
		}

		// If we allow Excel exports they need to be able to select it....
		// Unless we're going to display this inline, in which case they don't have a choice - its PDF only
		if ((IgniteConstants.FLAG_YES.equals(report.getAllowExcelExportInd())) &&(!isInline)) {
			// Add a standard parameter which allows the user to select the download type
			returnParameters++;
			index++;
			String label = "Generate as";
			String downloadType =
					"  <div class='row'>" +
							"    <input type='hidden' id='" + getElementName(index) + "_name' name='" + getElementName(index) + "_name' value='" + IgniteConstants.GENERATE_REPORT_AS_KEY + "'>" +
							"    <label class='col-md-3' for='" + getElementName(index) + "'>" + label + "</label>" +
							"    <div class='col-md-3'>" +
							"      <select id='" + getElementName(index) + "' name='" + getElementName(index) + "' class='form-control'>" +
							"        <option value='" + IgniteConstants.REPORT_TYPE_PDF + "' selected>Adobe PDF</option>" +
							"        <option value='" + IgniteConstants.REPORT_TYPE_EXCEL + "'>Microsoft Excel</option>" +
							"      </select>" +
							"    </div>" +
							"  </div>" +
							"</div>";

			if (!formOpened) {
				html += formOpeningHtml;
				formOpened = true;
			}

			html += downloadType;
		}

		if (formOpened) {
			html += "</fieldset>" +
					"</form>";
		}

		if (returnParameters == 0) {
			html = null;
		}

		return html;
	}

	private String getElementName(int index) {
		return "rp" + index;
	}

	private String getNumericInput(int index, ReportParameter parameter) {
		String html = "<input id='" + getElementName(index) + "' name='" + getElementName(index) + "' type='number' class='form-control'>";

		return html;
	}

	private String getCheckboxInput(int index, ReportParameter parameter) {
		String html = "<input id='" + getElementName(index) + "' name='" + getElementName(index) + "' type='checkbox'>";

		return html;
	}

	private String getDateInput(int index, ReportParameter parameter) {
		// TODO: change this to datepicker
		String html = "<input id='" + getElementName(index) + "' name='" + getElementName(index) + "' type='date' class='form-control'>";

		return html;
	}

	private String getSelect(int index, ReportParameter parameter) {
		String html = "<select id='" + getElementName(index) + "' name='" + getElementName(index) + "' class='form-control'>" +
				"<option value='' selected></option>";  // add first blank selected option

		Map<String, Object> data = databaseService.executeQuery(parameter.getSelectSql());

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> columns = (ArrayList<Map<String, Object>>) data.get("columns");

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> resultset = (ArrayList<Map<String, Object>>) data.get("resultset");

		Map<String, Object> col1 = columns.get(0);
		Map<String, Object> col2 = columns.get(1);

		String idCol = (String) col1.get("name");
		String textCol = (String) col2.get("name");

		Collections.sort(resultset, new Comparator<Map<String, Object>>() {

			@SuppressWarnings("rawtypes")
			@Override
			public int compare(Map o1, Map o2) {
				String o1Text = o1.get(textCol) == null ? "" : (String) o1.get(textCol);
				String o2Text = o2.get(textCol) == null ? "" : (String) o2.get(textCol);

				return o1Text.compareTo(o2Text);
			}

		});

		for (Map<String, Object> row : resultset) {
			String id = (String) row.get(idCol);
			String text = (String) row.get(textCol);

			html += "<option value='" + id + "'>" + text + "</option>";
		}

		html += "</select>";

		return html;
	}

	private String getInput(int index, ReportParameter parameter) {
		String html = "<input id='" + getElementName(index) + "' name='" + getElementName(index) + "' type='text' class='form-control'>";

		return html;
	}

	public boolean fileExists(Report report) {
		String filename = IgniteUtils.getTerminatedPath(reportsPath) + report.getReportFilename() + IgniteConstants.JASPER_SRC_EXTENSION;
		File f = new File(filename);

		return f.exists();
	}

	public void updateReportStatuses() {
		List<Report> reports = reportRepository.findAll();

		for (Report report: reports) {
			String fileExistsInd = fileExists(report) ? IgniteConstants.FLAG_YES : IgniteConstants.FLAG_NO;

			if (fileExistsInd.equalsIgnoreCase(report.getFileExistsInd())) {
				// no need to update it if its already set...
				continue;
			}

			report.setFileExistsInd(fileExistsInd);
			reportRepository.save(report);
		}
	}

	public int getMissingReportCount() {
		int missingReports = 0;

		List<Report> reports = reportRepository.findAll();
		for (Report report: reports) {
			if (!fileExists(report)) {
				missingReports++;
			}
		}

		return missingReports;
	}

	public void uploadReport(MultipartFile multipartFile) throws IOException {
		byte[] buffer = new byte[1024]; // This buffer will store the data read from the file
		String filename = IgniteUtils.getTerminatedPath(getReportsPath()) + multipartFile.getOriginalFilename();

		// Now create the output file on the server.
		File outputFile = new File(filename);

		InputStream reader = null;
		FileOutputStream writer = null;
		long totalBytes = 0;

		try {
			outputFile.createNewFile();
			reader = multipartFile.getInputStream(); // Create the input stream to uploaded file to read data from it
			writer = new FileOutputStream(outputFile); // Create writer for 'outputFile' to write data read from the file

			int bytesRead = 0;
			// Iteratively read data from the file and write to 'outputFile'
			while ((bytesRead = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, bytesRead);
				totalBytes += bytesRead;
			}

			writer.flush();
		} finally {
			IgniteUtils.closeQuietly(reader);
			IgniteUtils.closeQuietly(writer);
		}

		logger.info(filename + " written: " + totalBytes + " bytes");
	}
}
