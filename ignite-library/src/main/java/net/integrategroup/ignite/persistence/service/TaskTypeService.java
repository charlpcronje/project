package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.TaskType;

public interface TaskTypeService {

	List<TaskType> getTaskType();

	//void delete(ExpenseTypeParent expenseTypeParent);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	TaskType findByTaskTypeId(Long taskTypeId);

	List<TaskType> findByAssignmentTypeId(Long assignmentTypeId);

	TaskType save(TaskType taskType);

}
