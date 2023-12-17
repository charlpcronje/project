package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsDto;
import net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.VPpExpenseRateUplineRecursive;
import net.integrategroup.ignite.persistence.model.VProjectTimeCost;

@Repository
public interface FinancialsRepository extends CrudRepository<VProjectTimeCost, Long> {

	// Time Related Costs
	// Table 1
	@Query("SELECT new net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsDto( "
			+ "	v.agreementParticipantIdPayer, "
			+ "	v.agreementPayer,"
			+ "	v.agreementParticipantIdBeneficiary, "
			+ "	v.agreementBeneficiary,"
			+ "	SUM(v.sumNrOfUnits), "
			+ "	SUM(v.lineAmount), "
			+ "	SUM(v.ratesMissing)) "
			+ " FROM VParticipantTimeCostTotals v "
			+ " WHERE v.agreementParticipantIdPayer = ?1"
			+ " AND v.activityDate BETWEEN ?2 AND ?3 "
			+ " GROUP BY "
			+ "	v.agreementParticipantIdPayer, "
			+ "	v.agreementPayer,"
			+ "	v.agreementParticipantIdBeneficiary, "
			+ "	v.agreementBeneficiary ")

	List<ParticipantTimeCostTotalsDto> findAgreementRelationshipsForParticipant(Long participantId, Date firstDay, Date lastDay);

	// ParticipantTimeCostTotalsPerProjectDto probleempie
	// Table 2 Time Cost
	@Query("SELECT new net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto( "
			+ "	v.projectId,"
			+ "	v.projectName, "
			+ "	v.sdName,"
			+ "	v.unitTypeName,"
			+ "	v.agreementBetweenParticipantsId,"
			+ "	v.agreementBetween,"
			+ "	v.agreementParticipantIdPayer,"
			+ "	v.agreementPayer,"
			+ "	v.agreementParticipantIdBeneficiary,"
			+ "	v.agreementBeneficiary,"
			+ "	v.projectSdId,"
			+ "	v.remunerationTypeName,"
			+ "	SUM(v.sumNrOfUnits) as sumNrOfUnits, "
			+ "	SUM(v.lineAmount) as lineAmount,"
			+ "	SUM(v.ratesMissing) as ratesMissing)"
			+ " FROM VParticipantTimeCostTotalsPerProject v "
			+ " WHERE  v.agreementParticipantIdPayer = ?1 "
			+ " AND v.agreementParticipantIdBeneficiary = ?2"
			+ " AND v.activityDate BETWEEN ?3 AND ?4 "
			+ " GROUP BY "
			+ "	v.projectId,"
			+ "	v.projectName, "
			+ "	v.sdName,"
			+ "	v.unitTypeName,"
			+ "	v.agreementBetweenParticipantsId,"
			+ "	v.agreementBetween,"
			+ "	v.agreementParticipantIdPayer,"
			+ "	v.agreementPayer,"
			+ "	v.agreementParticipantIdBeneficiary,"
			+ "	v.agreementBeneficiary,"
			+ "	v.projectSdId,"
			+ "	v.remunerationTypeName")

	List<ParticipantTimeCostTotalsPerProjectDto> findPayerBenAgreementTimeCost(Long agreementParticipantIdPayer, Long agreementParticipantIdBeneficiary, Date firstDay, Date lastDay);


	// Expense Claims: Table 1 High level summary
	@Query("SELECT new net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsDto( "
			+ "	v.participantIdContracting, "
			+ "	v.participantInAgreementContracting,"
			+ "	v.participantIdContracted, "
			+ "	v.participantInAgreementContracted,"
			+ "	SUM(v.sumNrOfUnits), "
			+ "	SUM(v.lineAmount), "
			+ "	SUM(v.ratesMissing)) "
			+ " FROM VParticipantExpenseCostTotals v "
			+ " WHERE v.participantIdContracting = ?1"
			+ " AND v.purchaseDate BETWEEN ?2 AND ?3 "
			+ " GROUP BY "
			+ "	v.participantIdContracting, "
			+ "	v.participantInAgreementContracting,"
			+ "	v.participantIdContracted, "
			+ "	v.participantInAgreementContracted")
	List<ParticipantExpenseCostTotalsDto> findAgreementRelationshipsForParticipantExpenseClaims(Long participantIdContracting, Date firstDay, Date lastDay);

	// Expense Claims: Table 2: Summary per Project

	@Query("SELECT new net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsPerProjectDto( "
			+ "	v.projectId,"
			+ "	v.projectName, "
			+ "	v.recoverableExpenseId,"
			+ "	v.expenseTypeId,"
			+ "	v.expenseTypeName,"
			+ "	v.unitTypeCode,"
			+ "	v.unitTypeName,"
			+ "	v.participantIdContracting,"
			+ "	v.participantInAgreementContracting,"
			+ "	v.participantIdContracted,"
			+ "	v.participantInAgreementContracted,"
			+ "	SUM(v.sumNrOfUnits), "
			+ "	SUM(v.lineAmount),"
			+ "	SUM(v.ratesMissing))"
			+ " FROM VParticipantExpenseCostTotalsPerProject v "
			+ " WHERE  v.participantIdContracting = ?1 "
			+ " AND v.participantIdContracted = ?2"
			+ " AND v.purchaseDate BETWEEN ?3 AND ?4 "
			+ " GROUP BY "
			+ "	v.projectId,"
			+ "	v.projectName, "
			+ "	v.recoverableExpenseId,"
			+ "	v.expenseTypeId,"
			+ "	v.expenseTypeName,"
			+ "	v.unitTypeCode,"
			+ "	v.unitTypeName,"
			+ "	v.participantIdContracting,"
			+ "	v.participantInAgreementContracting,"
			+ "	v.participantIdContracted,"
			+ "	v.participantInAgreementContracted")
	List<ParticipantExpenseCostTotalsPerProjectDto> findPayerBenAgreementExpenseCost(Long participantIdContracting, Long participantIdContracted, Date firstDay, Date lastDay);

	@Query("SELECT v "
			+ " FROM VPpExpenseRateUplineRecursive v"
			+ " WHERE  	v.expenseTypeId = ?1 "
			+ " AND 	v.participantIdContracting = ?4 "
			+ " AND 	v.participantIdContracted = ?5"
			+ " AND 	v.purchaseDate BETWEEN ?2 AND ?3 "
			+ " ORDER BY 	v.purchaseDate")
	List<VPpExpenseRateUplineRecursive> findContractingContractedExpenseCost(Long expenseTypeId, Date fd, Date ld, Long participantIdContracting, Long participantIdContracted);

}
