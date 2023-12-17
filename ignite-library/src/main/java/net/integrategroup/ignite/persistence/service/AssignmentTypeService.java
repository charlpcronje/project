package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.AssignmentType;

public interface AssignmentTypeService {

	List<AssignmentType> getAssignmentType();

	//void delete(ExpenseTypeParent expenseTypeParent);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	AssignmentType findByAssignmentTypeId(Long assignmentTypeId);

	AssignmentType save(AssignmentType assignmentType);

}
