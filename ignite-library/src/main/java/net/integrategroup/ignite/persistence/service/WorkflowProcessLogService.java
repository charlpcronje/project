package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.WorkflowProcessLog;

public interface WorkflowProcessLogService {

	List<WorkflowProcessLog> getWorkflowProcessLogEntries(long workflowProcessId);

}
