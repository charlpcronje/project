package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.BankStatement;

public interface BankStatementService {

	List<BankStatement> findAll();

	BankStatement findByBankStatementId(Long bankStatementId);

	BankStatement save(BankStatement bankStatement);

	List<BankStatement>  getAllBankStatements(Long participantBankDetailsId, Date firstDay, Date lastDay);

//	List<BankStatement>  getAllBankStatementsStatement(Long bankStatementId);

//	List<BankStatement>  getAllBankStatementsDates(Long participantBankDetailsId, Date firstDay, Date lastDay);


//	BankStatement getLastBankStatement(Long participantBankDetailsId);

}
