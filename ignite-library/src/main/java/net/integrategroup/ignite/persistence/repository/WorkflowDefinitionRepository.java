package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import net.integrategroup.ignite.persistence.model.WorkflowDefinition;
import net.integrategroup.ignite.persistence.model.WorkflowDefinitionStep;

public interface WorkflowDefinitionRepository extends CrudRepository<WorkflowDefinition, Long> {

	@Override
	WorkflowDefinition save(WorkflowDefinition workflowDefinition);

	@Override
	@Query("SELECT wd FROM WorkflowDefinition wd")
	List<WorkflowDefinition> findAll();

	@Query("SELECT " +
			"    wd " +
			"  FROM " +
			"    WorkflowDefinition wd" +
			"  WHERE" +
			"    workflowDefinitionId = ?1")
	WorkflowDefinition findByWorkflowDefinitionId(long workflowDefinitionId);

	@Query("SELECT" +
			"    wds" +
			"  FROM" +
			"    WorkflowDefinitionStep wds" +
			"  WHERE" +
			"    workflowDefinitionId = ?1 AND" +
			"    workflowDefinitionStepNumber = ?2")
	WorkflowDefinitionStep findWorkflowDefinitionStep(Long workflowDefinitionId, Integer workflowDefinitionStepNumber);

	@Query("SELECT" +
			"    wd" +
			"  FROM" +
			"    WorkflowDefinition wd" +
			"  WHERE " +
			"    workflowDefinitionCode = ?1")
	WorkflowDefinition findByWorkflowDefinitionCode(String workflowDefinitionCode);

	@Query("SELECT COUNT(1) AS record_count " +
			"  FROM " +
			"    WorkflowProcess wp" +
			"  WHERE" +
			"    workflowDefinitionId = ?1 AND" +
			"    activeFlag = 'Y'")
	int countWorkflowProcessesByWorkflowDefinitionId(long workflowDefinitionId);

}