package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.TaskImportanceType;

public interface TaskImportanceTypeService {

	List<TaskImportanceType> getTaskImportanceType();

	//void delete(ExpenseTypeParent expenseTypeParent);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	TaskImportanceType findByTaskImportanceTypeId(Long taskImportanceTypeId);

	TaskImportanceType save(TaskImportanceType taskImportanceType);

}
