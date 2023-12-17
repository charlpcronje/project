package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.BankStatement;


@Repository
public interface BankStatementRepository extends CrudRepository<BankStatement, Long> {

	@Override
	List<BankStatement> findAll();

	@Query("SELECT a FROM BankStatement a WHERE a.bankStatementId = ?1")
	BankStatement findByBankStatementId(Long bankStatementId);

//	@Query("SELECT a FROM BankStatement a WHERE a.participantBankDetailsId = ?1 order by a.statementNumber ASC")
//	List<BankStatement> getAllBankStatements(Long participantBankDetailsId);

	@Query("SELECT a FROM BankStatement a WHERE a.participantBankDetailsId = ?1"
			+ " AND a.statementDate BETWEEN ?2 AND ?3 order by a.statementDate ASC")
	List<BankStatement> getAllBankStatements(Long participantBankDetailsId, Date firstDay, Date lastDay);


}


