package net.integrategroup.ignite.persistence.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.WorkflowDefinition;
import net.integrategroup.ignite.persistence.model.WorkflowDefinitionStep;
import net.integrategroup.ignite.persistence.repository.WorkflowDefinitionRepository;
import net.integrategroup.ignite.persistence.repository.WorkflowDefinitionStatusRepository;
import net.integrategroup.ignite.persistence.repository.WorkflowDefinitionStepRepository;

@Service
public class WorkflowDefinitionServiceImpl implements WorkflowDefinitionService {

	@Autowired
	WorkflowDefinitionRepository workflowDefinitionRepository;

	@Autowired
	WorkflowDefinitionStepRepository workflowDefinitionStepRepository;

	@Autowired
	WorkflowDefinitionStatusRepository workflowDefinitionStatusRepository;

	@Override
	public List<WorkflowDefinition> findAll() {
		return workflowDefinitionRepository.findAll();
	}

	@Override
	public WorkflowDefinition findByWorkflowDefinitionId(Long workflowDefinitionId) {
		return workflowDefinitionRepository.findByWorkflowDefinitionId(workflowDefinitionId);
	}

	@Override
	public WorkflowDefinitionStep findWorkflowDefinitionStep(Long workflowDefinitionId, Integer workflowDefinitionStepNumber) {
		return workflowDefinitionRepository.findWorkflowDefinitionStep(workflowDefinitionId, workflowDefinitionStepNumber);
	}

	@Override
	public WorkflowDefinition findByWorkflowDefinitionId(long workflowDefinitionId) {
		return workflowDefinitionRepository.findByWorkflowDefinitionId(workflowDefinitionId);
	}

	@Override
	public WorkflowDefinition findByWorkflowDefinitionCode(String workflowDefinitionCode) {
		return workflowDefinitionRepository.findByWorkflowDefinitionCode(workflowDefinitionCode);
	}

	@Override
	public WorkflowDefinition save(WorkflowDefinition workflowDefinition) {
		return workflowDefinitionRepository.save(workflowDefinition);
	}

	@Override
	public String getWorkflowDefinitionStatus(long workflowDefinitionId) {
		return workflowDefinitionStatusRepository.getStatus(workflowDefinitionId);
	}

	@Override
	public int countWorkflowProcessesByWorkflowDefinitionId(long workflowDefinitionId) {
		return workflowDefinitionRepository.countWorkflowProcessesByWorkflowDefinitionId(workflowDefinitionId);
	}

	@Override
	public void delete(Long workflowDefinitionId) {
		WorkflowDefinition entity = workflowDefinitionRepository.findByWorkflowDefinitionId(workflowDefinitionId);
		if (entity != null) {

			// Delete the steps first
			workflowDefinitionStepRepository.deleteByWorkflowDefinitionId(workflowDefinitionId);

			workflowDefinitionRepository.delete(entity);
		}
	}
}