package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.BankTransactionLink;
import net.integrategroup.ignite.persistence.model.VBankTransactionLink;

@Repository
public interface BankTransactionLinkRepository extends CrudRepository<BankTransactionLink, Long> {

	@Override
	List<BankTransactionLink> findAll();

	@Query("SELECT btl FROM BankTransactionLink btl WHERE bankTransactionLinkId = ?1")
	BankTransactionLink findByBankTransactionLinkId(Long bankTransactionLinkId);

	@Query("SELECT btl FROM VBankTransactionLink btl where participantBankDetailsId = ?1")
	List<VBankTransactionLink> getBankTransactionLinkForParticipantBankDetailsId(Long participantBankDetailsId);

	@Query("SELECT btl FROM VBankTransactionLink btl where bankTransactionId = ?1")
	List<VBankTransactionLink> getBankTransactionLinkForBankTransactionId(Long bankTransactionId);

}
