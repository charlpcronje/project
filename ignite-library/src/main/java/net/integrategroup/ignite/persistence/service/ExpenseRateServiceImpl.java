package net.integrategroup.ignite.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.VExpenseRate;
import net.integrategroup.ignite.persistence.repository.ExpenseRateRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

/**
 * @author Ingrid Marais
 *
 */
@Service
public class ExpenseRateServiceImpl implements ExpenseRateService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ExpenseRateRepository expenseRateRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public List<VExpenseRate> getExpenseRates(Long recoverableExpenseId){
		return expenseRateRepository.getExpenseRates(recoverableExpenseId);
	}

	@Override
	public List<VExpenseRate> getExpenseRatesCurrent(Long recoverableExpenseId){
		return expenseRateRepository.getExpenseRatesCurrent(recoverableExpenseId);
	}


}

