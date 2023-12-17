package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ParticipantBankDetails;
import net.integrategroup.ignite.persistence.model.VParticipantBankDetails;
import net.integrategroup.ignite.persistence.repository.ParticipantBankDetailsRepository;

@Service
public class ParticipantBankDetailsServiceImpl implements ParticipantBankDetailsService {

	@Autowired
	ParticipantBankDetailsRepository participantBankDetailsRepository;

	@Override
	public List<ParticipantBankDetails> findAll() {
		return participantBankDetailsRepository.findAll();
	}

	@Override
	public ParticipantBankDetails save(ParticipantBankDetails participantBankDetails) {
		return participantBankDetailsRepository.save(participantBankDetails);
	}

	@Override
	public List<VParticipantBankDetails> findByProjectId(Long projectId) {
		return participantBankDetailsRepository.findByProjectId(projectId);
	}


	@Override
	public ParticipantBankDetails findByParticipantBankDetailsId(Long participantBankdDetailsId) {
		return participantBankDetailsRepository.findByParticipantBankDetailsId(participantBankdDetailsId);
	}

	@Override
	public List<VParticipantBankDetails> findParticipantBankDetails(Long participantId) {
		return participantBankDetailsRepository.findParticipantBankDetails(participantId);
	}

	//	@Override
	//	public List<ParticipantBankDetails> getParticipantBankDetailsForIndividual(Long individualId) {
	//		return participantBankDetailsRepository.getParticipantBankDetailsForIndividual(individualId);
	//	}

	//@Override
	//public void delete(ParticipantBankDetails participantBankDetails) {
	//	participantBankDetailsRepository.delete(participantBankDetails);
	//}
}
