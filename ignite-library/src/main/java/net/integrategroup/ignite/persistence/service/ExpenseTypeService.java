package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ExpenseType;
import net.integrategroup.ignite.persistence.model.UnitType;

public interface ExpenseTypeService {

	List<ExpenseType> findAll();

	List<ExpenseType> findByExpenseTypeParentId(Long expenseTypeParentId);

	ExpenseType findByExpenseTypeId(Long expenseTypeId);

	ExpenseType findByExpenseTypeParentIdAndId(Long expenseTypeParentId, Long expenseTypeId);

	ExpenseType save(ExpenseType expenseType);

	//void delete(ExpenseType expenseType);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	UnitType findUnitByExpenseTypeId(Long expenseTypeId);

	List<ExpenseType> findAllExceptAllowance();

	List<ExpenseType> findOnlyAllowanceExpenseTypes();


}
