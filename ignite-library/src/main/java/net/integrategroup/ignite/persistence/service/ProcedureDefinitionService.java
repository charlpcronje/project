package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ProcedureDefinition;

public interface ProcedureDefinitionService {

	List<ProcedureDefinition> findAll();

	ProcedureDefinition save(ProcedureDefinition procedureDefinition);

	ProcedureDefinition findByProcedureDefinitionId(Long procedureDefinition);

	//void delete(ProcedureDefinition procedureDefinition);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
