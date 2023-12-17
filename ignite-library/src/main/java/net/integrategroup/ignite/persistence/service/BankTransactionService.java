package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.BankTransaction;
import net.integrategroup.ignite.persistence.model.VBankTransaction;

public interface BankTransactionService {

	List<BankTransaction> findAll();

	BankTransaction findByBankTransactionId(Long bankTransactionId);

	BankTransaction save(BankTransaction bankTransaction);

	List<VBankTransaction>  getAllBankTransactions(Long participantBankDetailsId);

	List<VBankTransaction>  getAllBankTransactionsStatement(Long bankStatementId);

	List<VBankTransaction>  getAllBankTransactionsDates(Long participantBankDetailsId, Date firstDay, Date lastDay);

	BankTransaction getLastBankTransaction(Long participantBankDetailsId);

	List<VBankTransaction> getBankTransactionsByUsername(String username);

	List<VBankTransaction> getBankTransactionsByUsernameAndCardNumber(String username, String cardNumber);

	Long saveTransaction(String projectName, String cardNumber, String description, String amount, String username);

}
