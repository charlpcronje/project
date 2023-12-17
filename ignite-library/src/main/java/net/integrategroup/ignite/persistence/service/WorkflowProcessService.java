package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.WorkflowProcess;

public interface WorkflowProcessService {

	WorkflowProcess findByWorkflowProcessId(Long workflowProcessId);

	void terminateWorkflowProcess(Long workflowProcessId, String username);

	List<WorkflowProcess> findByStartDateAndEndDate(Date startDate, Date endDate);

	List<WorkflowProcess> findByStartDateEndDateAndActiveFlag(Date startDate, Date endDate, String activeFlag);

}
