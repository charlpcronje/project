package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ParticipantBankDetails;
import net.integrategroup.ignite.persistence.model.VParticipantBankDetails;

@Repository
public interface ParticipantBankDetailsRepository extends CrudRepository<ParticipantBankDetails, Long> {

	@Override
	List<ParticipantBankDetails> findAll();

//	@Query("SELECT pbd FROM Individual i, Participant p, ParticipantBankDetails pbd, Bank br WHERE"
//			+ "    i.individualId = ?1 AND" + "    i.participantId = p.participantId AND"
//			+ "    p.isIndividual = 'Y' AND" + "    p.participantId = pbd.participantIdOwner AND"
//			+ "    pbd.bankId = br.bankId")
//	List<ParticipantBankDetails> getParticipantBankDetailsForIndividual(Long individualId);

	@Query("SELECT bc FROM VParticipantBankDetails bc "
			+ "WHERE bc.participantIdOwner in (select pp.participantId from ProjectParticipant pp where pp.projectId = ?1)")
	List<VParticipantBankDetails> findByProjectId(Long projectId);

	@Query("SELECT pbd FROM ParticipantBankDetails pbd "
			+ "	WHERE pbd.participantBankDetailsId = ?1")
	ParticipantBankDetails findByParticipantBankDetailsId(Long ParticipantBankDetailsId);

	@Query("SELECT bc FROM VParticipantBankDetails bc "
			+ "WHERE bc.participantIdOwner = ?1")
	List<VParticipantBankDetails> findParticipantBankDetails(Long participantId);

}
