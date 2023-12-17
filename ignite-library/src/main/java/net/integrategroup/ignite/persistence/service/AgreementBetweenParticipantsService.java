package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.AgreementBetweenParticipants;
import net.integrategroup.ignite.persistence.model.AgreementRelationshipDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.RecoverableExpenseAgreementDto;
import net.integrategroup.ignite.persistence.model.VAgreementBetweenParticipants;
import net.integrategroup.ignite.persistence.model.VPPIndividualRatesUpline;
import net.integrategroup.ignite.persistence.model.VRecoverableExpenseAgreement;

public interface AgreementBetweenParticipantsService {


	AgreementBetweenParticipants save(AgreementBetweenParticipants agreementBetweenParticipants);

	AgreementBetweenParticipants findByAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId);

	List<VAgreementBetweenParticipants> findAllForProject(Long projectId);

	List<VAgreementBetweenParticipants> findAllForProjectParticipant(Long projectParticipantId);

	List<VAgreementBetweenParticipants> findAllTimeCostAgreementsForProject(Long projectId);

	List<VRecoverableExpenseAgreement> findAllExpenseAgreementsForProject(Long projectId);

	List<RecoverableExpenseAgreementDto> findDisctinctExpenseAgreementsForProject(Long projectId);

	List<AgreementRelationshipDto> findAgreementRelationshipsForProject(Long projectId);

	// ParticipantTimeCostTotalsPerProjectDto probleempie
	List<ParticipantTimeCostTotalsPerProjectDto> findProjectAgreementTimeCost(Long agreementBetweenParticipantsId);

	List<VPPIndividualRatesUpline> findProjectAgreementTimeCostDetail(Long agreementBetweenParticipantsId, Long projectSdId, Date lastDay);

}
