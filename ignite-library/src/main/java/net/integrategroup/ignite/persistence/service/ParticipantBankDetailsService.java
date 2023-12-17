package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ParticipantBankDetails;
import net.integrategroup.ignite.persistence.model.VParticipantBankDetails;

public interface ParticipantBankDetailsService {

	List<ParticipantBankDetails> findAll();

	ParticipantBankDetails findByParticipantBankDetailsId(Long participantBankDetailsId);

	ParticipantBankDetails save(ParticipantBankDetails participantBankDetails);

//	List<ParticipantBankDetails> getParticipantBankDetailsForIndividual(Long individualId);

	List<VParticipantBankDetails> findByProjectId(Long projectId);


	List<VParticipantBankDetails> findParticipantBankDetails(Long participantId);

	//void delete(ParticipantBankDetails participantBankDetails);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
