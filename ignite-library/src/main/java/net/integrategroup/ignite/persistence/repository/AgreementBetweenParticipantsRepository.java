package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.AgreementBetweenParticipants;
import net.integrategroup.ignite.persistence.model.AgreementRelationshipDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.RecoverableExpenseAgreementDto;
import net.integrategroup.ignite.persistence.model.VAgreementBetweenParticipants;
import net.integrategroup.ignite.persistence.model.VPPIndividualRatesUpline;
import net.integrategroup.ignite.persistence.model.VRecoverableExpenseAgreement;

@Repository
public interface AgreementBetweenParticipantsRepository extends CrudRepository<AgreementBetweenParticipants, Long> {

	@Query("SELECT a FROM AgreementBetweenParticipants a WHERE a.agreementBetweenParticipantsId = ?1")
	AgreementBetweenParticipants findByAgreementBetweenParticipantsId(Long AgreementBetweenParticipantsId);

	@Query("SELECT av "
			+ " FROM VAgreementBetweenParticipants av"
			+ " WHERE  av.projectId = ?1")
	List<VAgreementBetweenParticipants> findAllForProject(Long projectId);

	@Query("SELECT av "
			+ " FROM VAgreementBetweenParticipants av"
			+ " WHERE  av.projectParticipantId = ?1")
	List<VAgreementBetweenParticipants> findAllForProjectParticipant(Long projectParticipantId);

	@Query("SELECT av "
			+ " FROM VAgreementBetweenParticipants av"
			+ " WHERE  av.projectId = ?1 "
			+ " AND av.remunerationModelCode = 'TIME_COST'"
			+ " ORDER by av.level")
	List<VAgreementBetweenParticipants> findAllTimeCostAgreementsForProject(Long projectId);

	@Query("SELECT v "
			+ " FROM VRecoverableExpenseAgreement v"
			+ " WHERE  v.projectId = ?1")
	List<VRecoverableExpenseAgreement> findAllExpenseAgreementsForProject(Long projectId);

	@Query("SELECT DISTINCT new net.integrategroup.ignite.persistence.model.RecoverableExpenseAgreementDto( "
			+ " v.expenseTypeId,  "
			+ " v.expenseTypeName) "
			+ " FROM VRecoverableExpenseAgreement v"
			+ " WHERE  v.projectId = ?1")
	List<RecoverableExpenseAgreementDto> findDisctinctExpenseAgreementsForProject(Long projectId);

	@Query("SELECT DISTINCT new net.integrategroup.ignite.persistence.model.AgreementRelationshipDto( "
			+ " v.level,  "
			+ " v.projectParticipantId,  "
			+ " v.participantIdPayer,  "
			+ " v.systemNamePayer,  "
			+ " v.participantIdBeneficiary,  "
			+ " v.systemNameBeneficiary,  "
//			+ " v.projectId,  "
//			+ " v.projectName,  "
			+ " CONCAT('Level ', level, ': ',v.systemNamePayer, ' - ' ,v.systemNameBeneficiary))"
			+ " FROM VAgreementBetweenParticipants v"
			+ " WHERE  v.projectId = ?1"
			+ " ORDER BY v.level")
	List<AgreementRelationshipDto> findAgreementRelationshipsForProject(Long projectId);

//	@Query("SELECT ptcv "
//			+ " FROM VParticipantTimeCostTotalsPerProject ptcv"
//			+ " WHERE  ptcv.agreementBetweenParticipantsId = ?1 "
//			+ " ORDER by ptcv.sdName")
//	List<VParticipantTimeCostTotalsPerProject> findProjectAgreementTimeCost(Long agreementBetweenParticipantsId);

	// ParticipantTimeCostTotalsPerProjectDto probleempie
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
			+ " WHERE  v.agreementBetweenParticipantsId = ?1 "
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

	List<ParticipantTimeCostTotalsPerProjectDto> findProjectAgreementTimeCost(Long agreementBetweenParticipantsId);


	@Query("SELECT v "
			+ " FROM VPPIndividualRatesUpline v"
			+ " WHERE  v.agreementBetweenParticipantsId = ?1 "
			+ " AND  v.projectSdId = ?2 "
			+ " AND v.activityDate <= ?3"
			+ " ORDER by v.activityDate")
	List<VPPIndividualRatesUpline> findProjectAgreementTimeCostDetail(Long agreementBetweenParticipantsId, Long projectSdId, Date lastDay);


}
