package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.NonProjectRelatedAgreement;
import net.integrategroup.ignite.persistence.model.VHumanResourceUnionList;
import net.integrategroup.ignite.persistence.model.VNonProjectRelatedAgreement;
import net.integrategroup.ignite.persistence.repository.NonProjectRelatedAgreementRepository;

@Service
public class NonProjectRelatedAgreementServiceImpl implements NonProjectRelatedAgreementService {

	@Autowired
	NonProjectRelatedAgreementRepository nonProjectRelatedAgreementRepository;

	@Override
	public List<NonProjectRelatedAgreement> findAll() {
		return nonProjectRelatedAgreementRepository.findAll();
	}

	@Override
	public NonProjectRelatedAgreement save(NonProjectRelatedAgreement nonProjectRelatedAgreement) {
		return nonProjectRelatedAgreementRepository.save(nonProjectRelatedAgreement);
	}

	@Override
	public List<VNonProjectRelatedAgreement> getNonProjectRelatedAgreementForParticipant(Long participantId) {
		return nonProjectRelatedAgreementRepository.getNonProjectRelatedAgreementForParticipant(participantId);
	}

	@Override
	public NonProjectRelatedAgreement findByNonProjectRelatedAgreementId(Long nonProjectRelatedAgreementId) {
		return nonProjectRelatedAgreementRepository.findByNonProjectRelatedAgreementId(nonProjectRelatedAgreementId);
	}

	@Override
	public List<VHumanResourceUnionList> findHrByParticipantId(Long participantId) {
		return nonProjectRelatedAgreementRepository.findHrByParticipantId(participantId);
	}

	@Override
	public List<VNonProjectRelatedAgreement> getWhoParticipantIsAResourceOf(Long participantId) {
		return nonProjectRelatedAgreementRepository.getWhoParticipantIsAResourceOf(participantId);
	}

}
