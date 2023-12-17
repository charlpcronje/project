package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsDto;
import net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.VPpExpenseRateUplineRecursive;
import net.integrategroup.ignite.persistence.repository.FinancialsRepository;

// @author Ingrid Marais

@Service
public class FinancialsServiceImpl implements FinancialsService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	FinancialsRepository financialsRepository;


	// Time Related Costs:
	@Override
	public List<ParticipantTimeCostTotalsDto> findAgreementRelationshipsForParticipant(Long participantId, Date firstDay, Date lastDay) {
		return financialsRepository.findAgreementRelationshipsForParticipant(participantId, firstDay, lastDay);
	}

	// ParticipantTimeCostTotalsPerProjectDto probleempie
	@Override
		public List<ParticipantTimeCostTotalsPerProjectDto> findPayerBenAgreementTimeCost(Long agreementParticipantIdPayer, Long agreementParticipantIdBeneficiary, Date firstDay, Date lastDay) {
			return financialsRepository.findPayerBenAgreementTimeCost(agreementParticipantIdPayer,agreementParticipantIdBeneficiary, firstDay, lastDay);
		}

	// Expense Claims
	@Override
	public List<ParticipantExpenseCostTotalsDto> findAgreementRelationshipsForParticipantExpenseClaims(Long participantId, Date firstDay, Date lastDay) {
		return financialsRepository.findAgreementRelationshipsForParticipantExpenseClaims(participantId, firstDay, lastDay);
	}

	@Override
	public List<ParticipantExpenseCostTotalsPerProjectDto> findPayerBenAgreementExpenseCost(Long participantIdContracting, Long participantIdContracted, Date firstDay, Date lastDay) {
		return financialsRepository.findPayerBenAgreementExpenseCost(participantIdContracting,participantIdContracted, firstDay, lastDay);
	}


	@Override
	public List<VPpExpenseRateUplineRecursive> findContractingContractedExpenseCost(Long expenseTypeId, Date fd, Date ld, Long participantIdContracting, Long participantIdContracted) {
		return financialsRepository.findContractingContractedExpenseCost(expenseTypeId, fd, ld, participantIdContracting, participantIdContracted);
	}


}

