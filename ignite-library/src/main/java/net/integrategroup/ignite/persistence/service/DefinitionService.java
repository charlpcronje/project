package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Definition;

public interface DefinitionService {

	List<Definition> getDefinition();

	//void delete(ExpenseTypeParent expenseTypeParent);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	Definition findByDefinitionId(Long definitionId);

	List<Definition> findDefinitionNotLinked(Long genProcedureId);

	Definition save(Definition definition);

}
