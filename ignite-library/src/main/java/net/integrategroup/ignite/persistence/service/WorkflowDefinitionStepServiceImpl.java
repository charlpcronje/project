package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.WorkflowDefinitionStep;
import net.integrategroup.ignite.persistence.repository.WorkflowDefinitionStepRepository;

@Service
public class WorkflowDefinitionStepServiceImpl implements WorkflowDefinitionStepService {

	@Autowired
	WorkflowDefinitionStepRepository workflowDefinitionStepRepository;

	@Override
	public List<WorkflowDefinitionStep> findByWorkflowDefinitionId(long workflowDefinitionId) {
		return workflowDefinitionStepRepository.findByWorkflowDefinitionId(workflowDefinitionId);
	}

	@Override
	public void delete(Long workflowDefinitionId, Integer workflowDefinitionStepNumber) {
		WorkflowDefinitionStep entity = workflowDefinitionStepRepository.
				                                           findByWorkflowDefinitionIdAndWorkflowDefinitionStepNumber(workflowDefinitionId,
				                                        		                                                      workflowDefinitionStepNumber);
		if (entity != null) {
			workflowDefinitionStepRepository.delete(entity);
		}
	}

	@Override
	public WorkflowDefinitionStep findByWorkflowDefinitionIdAndWorkflowDefinitionStepNumber(Long workflowDefinitionId,
			                                                                                          Integer workflowDefinitionStepNumber) {
		return workflowDefinitionStepRepository.findByWorkflowDefinitionIdAndWorkflowDefinitionStepNumber(workflowDefinitionId,
				                                                                                          workflowDefinitionStepNumber);
	}

	@Override
	public WorkflowDefinitionStep save(WorkflowDefinitionStep workflowDefinitionStep) {
		return workflowDefinitionStepRepository.save(workflowDefinitionStep);
	}

}
