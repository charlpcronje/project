package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ExpenseType;
import net.integrategroup.ignite.persistence.model.ExpenseTypeParent;
import net.integrategroup.ignite.persistence.model.UnitType;

@Repository
public interface ExpenseTypeRepository extends CrudRepository<ExpenseType, Long> {

	@Override
	@Query("SELECT ep FROM ExpenseType ep ORDER BY Name")
	List<ExpenseType> findAll();

	@Query("SELECT ep FROM ExpenseTypeParent ep")
	List<ExpenseTypeParent> getExpenseTypeParents();

	@Query("SELECT e FROM ExpenseType e WHERE expenseTypeId = ?1")
	ExpenseType findByExpenseTypeId(Long expenseTypeId);

	@Query("SELECT e FROM ExpenseType e WHERE expenseTypeParentId = ?1 and expenseTypeId = ?2")
	ExpenseType findByExpenseTypeParentIdAndId(Long expenseTypeParentId, Long expenseTypeId);

	@Query("SELECT e FROM ExpenseType e WHERE expenseTypeParentId = ?1")
	List<ExpenseType> findByExpenseTypeParentId(Long expenseTypeParentId);

	@Query("SELECT u FROM UnitType u, ExpenseType e WHERE e.unitTypeCode = u.unitTypeCode AND e.expenseTypeId = ?1")
	UnitType findUnitByExpenseTypeId(Long expenseTypeId);

	@Query("SELECT e FROM ExpenseType e WHERE allowanceFlag = 'N'")
	List<ExpenseType> findAllExceptAllowance();

	@Query("SELECT e FROM ExpenseType e WHERE allowanceFlag = 'Y'")
	List<ExpenseType> findOnlyAllowanceExpenseTypes();

}
