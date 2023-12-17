package net.integrategroup.ignite.persistence.repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Repository;

@Repository
public class WorkflowDefinitionStatusRepository {

	@PersistenceContext
    private EntityManager entityManager;

	public String getStatus(Long workflowDefinitionId) {
		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("ig_db.WorkflowValidation");
		storedProcedure.registerStoredProcedureParameter(0, Long.class, ParameterMode.IN); // workflowDefinitionId
		storedProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT); // Status

		storedProcedure.setParameter(0, workflowDefinitionId);

		storedProcedure.execute();
		Object o = storedProcedure.getOutputParameterValue(1);
		String status = ((o == null) || (((String) o).isEmpty())) ? null : (String) o;

		return status;
	}

}
