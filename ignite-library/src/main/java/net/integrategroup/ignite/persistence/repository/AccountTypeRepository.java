package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.AccountType;

@Repository
public interface AccountTypeRepository extends CrudRepository<AccountType, Long> {

	@Override
	List<AccountType> findAll();


	@Query("SELECT cl FROM AccountType cl WHERE cl.accountTypeId = ?1")
	AccountType findByAccountTypeId(Long accountTypeId);

}
