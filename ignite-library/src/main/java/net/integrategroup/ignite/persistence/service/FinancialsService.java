package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsDto;
import net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.VPpExpenseRateUplineRecursive;

public interface FinancialsService {

	// Time Related Costs:
	List<ParticipantTimeCostTotalsDto> findAgreementRelationshipsForParticipant(Long participantId, Date firstDay, Date lastDay);

	// ParticipantTimeCostTotalsPerProjectDto probleempie
	List<ParticipantTimeCostTotalsPerProjectDto> findPayerBenAgreementTimeCost(Long agreementParticipantIdPayer, Long agreementParticipantIdBeneficiary, Date firstDay, Date lastDay);

	// Expense Costs:
	List<ParticipantExpenseCostTotalsDto> findAgreementRelationshipsForParticipantExpenseClaims(Long participantId, Date firstDay, Date lastDay);

	List<ParticipantExpenseCostTotalsPerProjectDto> findPayerBenAgreementExpenseCost(Long participantIdContracting, Long participantIdContracted, Date firstDay, Date lastDay);

	List<VPpExpenseRateUplineRecursive> findContractingContractedExpenseCost(Long expenseTypeId, Date fd, Date ld, Long participantIdContracting, Long participantIdContracted);


}
