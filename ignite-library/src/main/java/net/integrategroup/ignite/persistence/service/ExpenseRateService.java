package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.VExpenseRate;

public interface ExpenseRateService {

	List<VExpenseRate> getExpenseRates(Long recoverableExpenseId);

	List<VExpenseRate> getExpenseRatesCurrent(Long recoverableExpenseId);


}
