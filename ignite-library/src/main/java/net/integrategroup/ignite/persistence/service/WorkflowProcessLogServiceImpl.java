package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.WorkflowProcessLog;
import net.integrategroup.ignite.persistence.repository.WorkflowProcessLogRepository;

@Service
public class WorkflowProcessLogServiceImpl implements WorkflowProcessLogService {

	@Autowired
	WorkflowProcessLogRepository workflowProcessLogRepository;

	@Override
	public List<WorkflowProcessLog> getWorkflowProcessLogEntries(long workflowProcessId) {
		return workflowProcessLogRepository.getWorkflowProcessLogEntries(workflowProcessId);
	}

}
