package net.integrategroup.ignite.persistence.repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Repository;

@Repository
public class WorkflowDefinitionStepsToGraphRepository {
	@PersistenceContext
    private EntityManager entityManager;

	public String workflowDefinitionStepsToGraph(Long workflowDefinitionId, String workflowState, Integer nextStep) {
		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("wflow.WorkflowDefinitionStepsToGraph");

		storedProcedure.registerStoredProcedureParameter(0, Long.class, ParameterMode.IN);		// workflowDefinitionId
		storedProcedure.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);	// nextStep
		storedProcedure.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);	// state
		storedProcedure.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT);	// graphText

		storedProcedure.setParameter(0, workflowDefinitionId);
		storedProcedure.setParameter(1, nextStep);
		storedProcedure.setParameter(2, workflowState);

		storedProcedure.execute();

		String result = (String) storedProcedure.getOutputParameterValue(3);

		return result;
	}

}
