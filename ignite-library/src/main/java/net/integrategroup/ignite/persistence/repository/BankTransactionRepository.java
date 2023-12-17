package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.BankTransaction;
import net.integrategroup.ignite.persistence.model.VBankTransaction;


@Repository
public interface BankTransactionRepository extends CrudRepository<BankTransaction, Long> {

	@Override
	List<BankTransaction> findAll();

	@Query("SELECT a "
			+ " FROM BankTransaction a "
			+ " WHERE a.bankTransactionId = ?1")
	BankTransaction findByBankTransactionId(Long bankTransactionId);


	@Query("SELECT a "
			+ " FROM VBankTransaction a "
			+ " WHERE a.participantBankDetailsId = ?1 "
			+ " ORDER BY a.transactionDate, "
			+ " a.participantBankDetailsId ASC")
	List<VBankTransaction> getAllBankTransactions(Long participantBankDetailsId);

	@Query("SELECT a "
			+ " FROM VBankTransaction a "
			+ " WHERE a.bankStatementId = ?1 "
			+ " ORDER BY a.transactionDate, "
			+ " a.participantBankDetailsId ASC")
	List<VBankTransaction> getAllBankTransactionsStatement(Long bankStatementId);

	@Query("SELECT a "
			+ " FROM VBankTransaction a "
			+ " WHERE a.participantBankDetailsId = ?1"
			+ " AND a.transactionDate BETWEEN ?2 AND ?3"
			+ " ORDER BY a.transactionDate ASC, a.participantBankDetailsId ASC")
	List<VBankTransaction> getAllBankTransactionsDates(Long participantBankDetailsId, Date firstDay, Date lastDay);

	@Query("SELECT a "
			+ " FROM BankTransaction a "
			+ " WHERE a.transactionDate = (SELECT max(b.transactionDate) FROM BankTransaction b "
										+	"WHERE b.participantBankDetailsId = ?1 )"
			+   "AND a.participantBankDetailsId = ?1"
			+   "AND a.bankTransactionId = (SELECT max(c.bankTransactionId) FROM BankTransaction c "
										+	"WHERE c.participantBankDetailsId = ?1 "
										+   "AND c.transactionDate = (SELECT max(d.transactionDate) FROM BankTransaction d "
										+	"WHERE d.participantBankDetailsId = ?1 ))")
	BankTransaction getLastBankTransaction(Long participantBankDetailsId);

	// TODO: We may need to consider only fetching this months transactions
	@Query("SELECT a "
			+ " FROM VBankTransaction a, ParticipantBankDetails pbd,Individual i "
			+ " WHERE "
			+ "a.participantBankDetailsId = pbd.participantBankDetailsId "
			+ "AND pbd.participantIdOwner = i.participantId "
			+ "AND i.userName = ?1 "
			+ "ORDER BY "
			+ "  transactionDate DESC")
	List<VBankTransaction> getBankTransactionByUsername(String username);

	@Query("SELECT " 
	        + "    a "
			+ "  FROM " 
	        + "    VBankTransaction a, ParticipantBankDetails pbd, Individual i, BankCard bc "
			+ "  WHERE "
			+ "    a.participantBankDetailsId = pbd.participantBankDetailsId "
			+ "    AND pbd.participantIdOwner = i.participantId "
			+ "    AND bc.participantBankDetailsId = a.participantBankDetailsId"
			+ "    AND i.userName = ?1 "
			+ "    AND bc.cardNumber = ?2 "
			+ "  ORDER BY "
			+ "    transactionDate DESC")
	List<VBankTransaction> getBankTransactionsByUsernameAndCardNumber(String username, String cardNumber);

}


