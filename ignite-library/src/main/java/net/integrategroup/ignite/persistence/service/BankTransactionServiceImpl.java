package net.integrategroup.ignite.persistence.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.BankTransaction;
import net.integrategroup.ignite.persistence.model.VBankTransaction;
import net.integrategroup.ignite.persistence.repository.BankTransactionRepository;
import net.integrategroup.ignite.utils.IgniteUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

// @author Johannes

@Service
public class BankTransactionServiceImpl implements BankTransactionService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	BankTransactionRepository bankTransactionRepository;

	@Autowired
	SecurityUtils securityUtils;
	
	@Override
	public List<BankTransaction> findAll() {
		return bankTransactionRepository.findAll();
	}

	@Override
	public BankTransaction findByBankTransactionId(Long bankTransactionId) {
		return bankTransactionRepository.findByBankTransactionId(bankTransactionId);
	}

	@Override
	public BankTransaction save(BankTransaction bankTransaction) {
		return bankTransactionRepository.save(bankTransaction);
	}


	@Override
	public List<VBankTransaction> getAllBankTransactions(Long participantBankDetailsId){
		return bankTransactionRepository.getAllBankTransactions(participantBankDetailsId);
	}

	@Override
	public List<VBankTransaction> getAllBankTransactionsStatement(Long bankStatementId) {
		return bankTransactionRepository.getAllBankTransactionsStatement(bankStatementId);
	}

	@Override
	public List<VBankTransaction> getAllBankTransactionsDates(Long participantBankDetailsId, Date firstDay, Date lastDay) {
		return bankTransactionRepository.getAllBankTransactionsDates(participantBankDetailsId, firstDay, lastDay);
	}

	@Override
	public BankTransaction getLastBankTransaction(Long participantBankDetailsId) {
		return bankTransactionRepository.getLastBankTransaction(participantBankDetailsId);
	}

	@Override
	public List<VBankTransaction> getBankTransactionsByUsername(String username) {
		return bankTransactionRepository.getBankTransactionByUsername(username);
	}

	@Override
	public List<VBankTransaction> getBankTransactionsByUsernameAndCardNumber(String username, String cardNumber) {
		return bankTransactionRepository.getBankTransactionsByUsernameAndCardNumber(username, cardNumber);
	}

	@Override
	public Long saveTransaction(String projectName, String cardNumber, 
			                       String description, String amount, String username) {
		Long resultId = null;
		
		Connection conn = null;
		CallableStatement cstm = null;
		
		String sql = "CALL ig_db.saveNewBankTransaction(?, ?, ?, ?, ?, ?, ?);";

		try {
			conn = databaseService.getConnection();
			cstm = conn.prepareCall(sql);
				
			cstm.setString(1, projectName);
			cstm.setString(2, cardNumber);
			cstm.setString(3, description);
			cstm.setString(4, amount);
			cstm.setString(5, username);
			cstm.setString(5, securityUtils.getUsername());
			cstm.registerOutParameter(6, java.sql.Types.BIGINT);
	
			cstm.execute();
			resultId = cstm.getLong(3);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (cstm != null) {
					cstm.close();
				}
			} catch (Exception e) {
				//nothing
			}
		}
		return resultId;
	}
}

