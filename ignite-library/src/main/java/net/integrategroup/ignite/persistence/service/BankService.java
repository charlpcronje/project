package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Bank;

public interface BankService {

	List<Bank> findAll();

	Bank save(Bank bank);

	Bank findByBankId(Long bankId);

	//void delete(Bank bank);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
