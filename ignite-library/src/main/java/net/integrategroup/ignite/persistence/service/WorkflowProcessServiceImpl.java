package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.WorkflowProcess;
import net.integrategroup.ignite.persistence.repository.WorkflowProcessRepository;

@Service
public class WorkflowProcessServiceImpl implements WorkflowProcessService {

	@Autowired
	WorkflowProcessRepository workflowProcessRepository;

	@Override
	public WorkflowProcess findByWorkflowProcessId(Long workflowProcessId) {
		return workflowProcessRepository.findByWorkflowProcessId(workflowProcessId);
	}

	@Override
	public void terminateWorkflowProcess(Long workflowProcessId, String username) {
		workflowProcessRepository.terminateWorkflowProcess(workflowProcessId, username);
	}

	@Override
	public List<WorkflowProcess> findByStartDateAndEndDate(Date startDate, Date endDate) {
		return workflowProcessRepository.findByStartDateAndEndDate(startDate, endDate);
	}

	@Override
	public List<WorkflowProcess> findByStartDateEndDateAndActiveFlag(Date startDate, Date endDate, String activeFlag) {
		return workflowProcessRepository.findByStartDateEndDateAndActiveFlag(startDate, endDate, activeFlag);
	}

}
