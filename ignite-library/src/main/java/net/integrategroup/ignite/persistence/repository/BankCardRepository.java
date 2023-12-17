package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.BankCard;
import net.integrategroup.ignite.persistence.model.VBankCard;

@Repository
public interface BankCardRepository extends CrudRepository<BankCard, Long> {

	@Override
	List<BankCard> findAll();

	@Query("SELECT bc FROM VBankCard bc "
			+ "JOIN ParticipantBankDetails pbd ON bc.participantBankDetailsId = pbd.participantBankDetailsId "
			+ "WHERE pbd.participantIdOwner = ?1")
	List<VBankCard> findByParticipantId(Long participantId);

	@Query("SELECT bc FROM VBankCard bc "
			+ "JOIN ParticipantBankDetails pbd ON bc.participantBankDetailsId = pbd.participantBankDetailsId "
			+ "WHERE pbd.participantIdOwner in (select pp.participantId from ProjectParticipant pp where pp.projectId = ?1)")
	List<VBankCard> findByProjectId(Long projectId);

	@Query("SELECT bc FROM VBankCard bc WHERE participantBankDetailsId = ?1")
	List<VBankCard> findByParticipantBankDetailsId(Long participantBankDetailsId);

	@Query("SELECT bc FROM VBankCard bc WHERE individualIdMainCardUser = ?1")
	List<VBankCard> findByIndividualId(Long individualId);

	@Query("SELECT bc FROM BankCard bc WHERE bankCardId = ?1")
	BankCard findByBankCardId(Long bankCardId);

	@Query("SELECT " +
	       "    bc " +
		   "  FROM " +
	       "    VBankCard bc," +
	       "    Individual i " + 
	       "  WHERE " +
	       "    i.userName = ?1" +
	       "    AND bc.individualIdMainCardUser = i.individualId")
	List<VBankCard> findByUsername(String username);

}
