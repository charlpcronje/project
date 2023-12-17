package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ExpenseTypeParent;

@Repository
public interface ExpenseTypeParentRepository extends CrudRepository<ExpenseTypeParent, Long> {

	@Query("SELECT ep FROM ExpenseTypeParent ep")
	List<ExpenseTypeParent> getExpenseTypeParents();

	@Query("SELECT ep FROM ExpenseTypeParent ep WHERE expenseTypeParentId = ?1")
	ExpenseTypeParent findByExpenseTypeParentId(Long expenseTypeParentId);

}
