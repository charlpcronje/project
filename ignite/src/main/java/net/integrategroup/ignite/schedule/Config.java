package net.integrategroup.ignite.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import net.integrategroup.ignite.persistence.model.KeyValuePair;
import net.integrategroup.ignite.persistence.service.KeyValuePairService;
import net.integrategroup.ignite.utils.IgniteConstants;

// Source:
// https://www.callicoder.com/spring-boot-task-scheduling-with-scheduled-annotation/

@Configuration
public class Config implements SchedulingConfigurer {
	private final int POOL_SIZE = 10;

	@Autowired
	KeyValuePairService keyValuePairService;

	@Autowired
	HealthMonitorTask healthMonitorTask;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

		threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
		threadPoolTaskScheduler.setThreadNamePrefix("ignite-scheduled-task-pool-");
		threadPoolTaskScheduler.initialize();

		taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);

		// Source:
		// https://dzone.com/articles/multiple-cron-task-with-spring-boot-scheduler
		Trigger trigger = new Trigger() {

			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				KeyValuePair kvp = keyValuePairService.findByKeyName(IgniteConstants.KEY_SCHEDULE_HEALTH);

				// Note: we default the health monitoring task to start at 02h00
				String cronValue = (kvp == null) ? "0 2 * * * ?" : kvp.getValue();
				CronTrigger cronTrigger = new CronTrigger(cronValue);

				return cronTrigger.nextExecutionTime(triggerContext);
			}
		};

		taskRegistrar.addTriggerTask(healthMonitorTask, trigger);
	}

}
