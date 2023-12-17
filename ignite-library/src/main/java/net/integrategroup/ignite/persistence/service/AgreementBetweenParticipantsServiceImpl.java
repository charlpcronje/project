package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.AgreementBetweenParticipants;
import net.integrategroup.ignite.persistence.model.AgreementRelationshipDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.RecoverableExpenseAgreementDto;
import net.integrategroup.ignite.persistence.model.VAgreementBetweenParticipants;
import net.integrategroup.ignite.persistence.model.VPPIndividualRatesUpline;
import net.integrategroup.ignite.persistence.model.VRecoverableExpenseAgreement;
import net.integrategroup.ignite.persistence.repository.AgreementBetweenParticipantsRepository;

// @author Ingrid Marais

@Service
public class AgreementBetweenParticipantsServiceImpl implements AgreementBetweenParticipantsService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	AgreementBetweenParticipantsRepository agreementBetweenParticipantsRepository;


	@Override
	public AgreementBetweenParticipants save(AgreementBetweenParticipants agreementBetweenParticipants) {
		return agreementBetweenParticipantsRepository.save(agreementBetweenParticipants);
	}

	@Override
	public AgreementBetweenParticipants findByAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		return agreementBetweenParticipantsRepository.findByAgreementBetweenParticipantsId(agreementBetweenParticipantsId);
	}

	@Override
	public List<VAgreementBetweenParticipants> findAllForProject(Long projectId) {
		return agreementBetweenParticipantsRepository.findAllForProject(projectId);
	}

	@Override
	public List<VAgreementBetweenParticipants> findAllForProjectParticipant(Long projectParticipantsId) {
		return agreementBetweenParticipantsRepository.findAllForProjectParticipant(projectParticipantsId);
	}

	@Override
	public List<VAgreementBetweenParticipants> findAllTimeCostAgreementsForProject(Long projectId) {
		return agreementBetweenParticipantsRepository.findAllTimeCostAgreementsForProject(projectId);
	}

	@Override
	public List<VRecoverableExpenseAgreement> findAllExpenseAgreementsForProject(Long projectId) {
		return agreementBetweenParticipantsRepository.findAllExpenseAgreementsForProject(projectId);
	}


	@Override
	public List<RecoverableExpenseAgreementDto> findDisctinctExpenseAgreementsForProject(Long projectId) {
		return agreementBetweenParticipantsRepository.findDisctinctExpenseAgreementsForProject(projectId);
	}

	@Override
	public List<AgreementRelationshipDto> findAgreementRelationshipsForProject(Long projectId) {
		return agreementBetweenParticipantsRepository.findAgreementRelationshipsForProject(projectId);
	}

	// ParticipantTimeCostTotalsPerProjectDto probleempie
	@Override
	public List<ParticipantTimeCostTotalsPerProjectDto> findProjectAgreementTimeCost(Long agreementBetweenParticipantsId) {
		return agreementBetweenParticipantsRepository.findProjectAgreementTimeCost(agreementBetweenParticipantsId);
	}

	@Override
	public List<VPPIndividualRatesUpline> findProjectAgreementTimeCostDetail(Long agreementBetweenParticipantsId, Long projectSdId, Date lastDay){
		return agreementBetweenParticipantsRepository.findProjectAgreementTimeCostDetail(agreementBetweenParticipantsId, projectSdId, lastDay);
	}

}

