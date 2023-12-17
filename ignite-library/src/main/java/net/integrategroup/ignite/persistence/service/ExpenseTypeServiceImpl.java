package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ExpenseType;
import net.integrategroup.ignite.persistence.model.UnitType;
import net.integrategroup.ignite.persistence.repository.ExpenseTypeRepository;

@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

	@Autowired
	ExpenseTypeRepository expenseTypeRepository;

	@Override
	public List<ExpenseType> findAll() {
		return expenseTypeRepository.findAll();
	}


	@Override
	public List<ExpenseType> findByExpenseTypeParentId(Long expenseTypeParentId) {
		return expenseTypeRepository.findByExpenseTypeParentId(expenseTypeParentId);
	}

	@Override
	public ExpenseType findByExpenseTypeId(Long expenseTypeId) {
		return expenseTypeRepository.findByExpenseTypeId(expenseTypeId);
	}

	@Override
	public ExpenseType findByExpenseTypeParentIdAndId(Long expenseTypeParentId, Long expenseTypeId) {
		return expenseTypeRepository.findByExpenseTypeParentIdAndId(expenseTypeParentId, expenseTypeId);
	}

	@Override
	public ExpenseType save(ExpenseType expenseType) {
		return expenseTypeRepository.save(expenseType);
	}

	//@Override
	//public void delete(ExpenseType expenseType) {
	//	expenseTypeRepository.delete(expenseType);
	//}

	@Override
	public UnitType findUnitByExpenseTypeId(Long expenseTypeId) {
		return expenseTypeRepository.findUnitByExpenseTypeId(expenseTypeId);
	}


	@Override
	public List<ExpenseType> findAllExceptAllowance() {
		return expenseTypeRepository.findAllExceptAllowance();
	}

	@Override
	public List<ExpenseType> findOnlyAllowanceExpenseTypes() {
		return expenseTypeRepository.findOnlyAllowanceExpenseTypes();
	}


}
