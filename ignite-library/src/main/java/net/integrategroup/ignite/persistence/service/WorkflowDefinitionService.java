package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.WorkflowDefinition;
import net.integrategroup.ignite.persistence.model.WorkflowDefinitionStep;

public interface WorkflowDefinitionService {

	List<WorkflowDefinition> findAll();

	WorkflowDefinition findByWorkflowDefinitionId(Long workflowDefinitionId);

	WorkflowDefinition findByWorkflowDefinitionId(long workflowDefinitionId);

	WorkflowDefinitionStep findWorkflowDefinitionStep(Long workflowDefinitionId, Integer workflowDefinitionStepNumber);

	WorkflowDefinition findByWorkflowDefinitionCode(String workflowDefinitionCode);

	WorkflowDefinition save(WorkflowDefinition workflowDefinition);

	String getWorkflowDefinitionStatus(long workflowDefinitionId);

	int countWorkflowProcessesByWorkflowDefinitionId(long workflowDefinitionId);

	void delete(Long workflowDefinitionId);


}
