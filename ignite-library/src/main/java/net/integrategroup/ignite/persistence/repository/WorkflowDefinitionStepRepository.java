package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import net.integrategroup.ignite.persistence.model.WorkflowDefinitionStep;

public interface WorkflowDefinitionStepRepository extends CrudRepository<WorkflowDefinitionStep, Long> {

	@Query("SELECT " +
		       "      wds" +
			   "    FROM " +
		       "      WorkflowDefinitionStep wds" +
			   "    WHERE" +
		       "      workflowDefinitionId = ?1")
	List<WorkflowDefinitionStep> findByWorkflowDefinitionId(Long workflowDefinitionId);

	@Query("SELECT " +
	       "      wds" +
		   "    FROM " +
	       "      WorkflowDefinitionStep wds" +
		   "    WHERE" +
	       "      workflowDefinitionId = ?1 AND" +
		   "      workflowDefinitionStepNumber = ?2")
	WorkflowDefinitionStep findByWorkflowDefinitionIdAndWorkflowDefinitionStepNumber(Long workflowDefinitionId,
			                                                                         Integer workflowDefinitionStepNumber);

	@Transactional
	@Modifying
	@Query("DELETE" +
		   "    FROM " +
		   "      WorkflowDefinitionStep wds" +
		   "    WHERE" +
		   "      workflowDefinitionId = ?1")
	void deleteByWorkflowDefinitionId(Long workflowDefinitionId);

}
