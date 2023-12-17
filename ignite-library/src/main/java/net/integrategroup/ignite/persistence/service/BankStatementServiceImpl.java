package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.BankStatement;
import net.integrategroup.ignite.persistence.repository.BankStatementRepository;

// @author Johannes

@Service
public class BankStatementServiceImpl implements BankStatementService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	BankStatementRepository bankStatementRepository;

	@Override
	public List<BankStatement> findAll() {
		return bankStatementRepository.findAll();
	}

	@Override
	public BankStatement findByBankStatementId(Long bankStatementId) {
		return bankStatementRepository.findByBankStatementId(bankStatementId);
	}

	@Override
	public BankStatement save(BankStatement bankStatement) {
		return bankStatementRepository.save(bankStatement);
	}


	@Override
	public List<BankStatement> getAllBankStatements(Long participantBankDetailsId, Date firstDay, Date lastDay){
		return bankStatementRepository.getAllBankStatements(participantBankDetailsId, firstDay, lastDay);
	}

//	@Override
//	public List<BankStatement> getAllBankStatementsStatement(Long bankStatementId) {
//		return bankStatementRepository.getAllBankStatementsStatement(bankStatementId);
//	}
//
//	@Override
//	public List<BankStatement> getAllBankStatementsDates(Long participantBankDetailsId, Date firstDay, Date lastDay) {
//		return bankStatementRepository.getAllBankStatementsDates(participantBankDetailsId, firstDay, lastDay);
//	}
//
//
//
//	@Override
//	public BankStatement getLastBankStatement(Long participantBankDetailsId) {
//		return bankStatementRepository.getLastBankStatement(participantBankDetailsId);
//	}

}

