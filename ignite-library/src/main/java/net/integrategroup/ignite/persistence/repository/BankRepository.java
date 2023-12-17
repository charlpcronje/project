package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Bank;

@Repository
public interface BankRepository extends CrudRepository<Bank, Long> {

	@Override
	List<Bank> findAll();

	@Query("SELECT b FROM Bank b WHERE b.bankId = ?1")
	Bank findByBankId(Long bankId);

}
