package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ExpenseTypeParent;
import net.integrategroup.ignite.persistence.repository.ExpenseTypeParentRepository;

@Service
public class ExpenseTypeParentServiceImpl implements ExpenseTypeParentService {

	@Autowired
	ExpenseTypeParentRepository expenseTypeParentRepository;

	@Override
	public ExpenseTypeParent save(ExpenseTypeParent expenseTypeParent) {
		return expenseTypeParentRepository.save(expenseTypeParent);
	}

	//@Override
	//public void delete(ExpenseTypeParent expenseTypeParent) {
	//	expenseTypeParentRepository.delete(expenseTypeParent);
	//}

	@Override
	public List<ExpenseTypeParent> getExpenseTypeParents() {
		return expenseTypeParentRepository.getExpenseTypeParents();
	}

	@Override
	public ExpenseTypeParent findByExpenseTypeParentId(Long expenseTypeParentId) {
		return expenseTypeParentRepository.findByExpenseTypeParentId(expenseTypeParentId);
	}
}
