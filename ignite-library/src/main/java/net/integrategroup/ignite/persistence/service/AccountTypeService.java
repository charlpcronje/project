package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.AccountType;

public interface AccountTypeService {

	AccountType findByAccountTypeId(Long accountTypeId);

	List<AccountType> findAll();

	AccountType save(AccountType accountType);

	//void delete(Long accountTypeId);

}
