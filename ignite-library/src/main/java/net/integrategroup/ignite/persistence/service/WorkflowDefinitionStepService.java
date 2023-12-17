package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.WorkflowDefinitionStep;


public interface WorkflowDefinitionStepService {

	List<WorkflowDefinitionStep> findByWorkflowDefinitionId(long workflowDefinitionId);

	void delete(Long workflowDefinitionId, Integer workflowDefinitionStepNumber);

	WorkflowDefinitionStep findByWorkflowDefinitionIdAndWorkflowDefinitionStepNumber(Long workflowDefinitionId,
			                                                                         Integer workflowDefinitionStepNumber);

	WorkflowDefinitionStep save(WorkflowDefinitionStep workflowDefinitionStep);

}
