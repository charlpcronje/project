package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.RemunerationRateUpline;
import net.integrategroup.ignite.persistence.model.VAgreementParticipants;
import net.integrategroup.ignite.persistence.model.VPPIndividualRatesUpline;
import net.integrategroup.ignite.persistence.model.VRemunerationRateUpline;

@Repository
public interface RemunerationRateUplineRepository extends CrudRepository<RemunerationRateUpline, Long> {


	@Query("SELECT vppr FROM VAgreementParticipants vppr,"
			+ " AgreementBetweenParticipants a"
			+ " WHERE vppr.projectId = ?1"
			+ " AND a.projectParticipantId = vppr.projectParticipantIdContracted"
			+ "	AND a.remunerationModelCode = 'TIME_COST'")

	List<VAgreementParticipants> getPPIndividualListWithTimeCostAgreements(Long projectId);


	@Query("SELECT p FROM VPPIndividualRatesUpline p WHERE "
			+ "p.timesheetId = ?1")
	List<VPPIndividualRatesUpline> getRatesUplineForTimesheetEntry(Long timesheetId);

	@Query("SELECT v FROM VAgreementParticipants v"
			+ " WHERE v.agreementBetweenParticipantsId = ?1 "
			+ " AND v.isIndividual = 'Y'")
	List<VAgreementParticipants> getAgreementIndividualList(Long agreementBetweenParticipantsId);

	@Query("SELECT v FROM VAgreementParticipants v"
			+ " WHERE v.agreementBetweenParticipantsId = ?1 ")
	List<VAgreementParticipants> getAgreementParticipantList(Long agreementBetweenParticipantsId);

	@Query("SELECT r FROM VRemunerationRateUpline r,"
			+ " ProjectParticipantSdRole psdr "
			+ " WHERE r.agreementBetweenParticipantsId = ?1"
			+ " AND r.projectParticipantSdRoleIdIndividual = psdr.projectParticipantSdRoleId "
			+ " AND psdr.projectParticipantId = ?2")
	List<VRemunerationRateUpline> getRatesUpline(Long agreementBetweenParticipantsId, Long projectParticipantId);

	@Query("SELECT r FROM VRemunerationRateUpline r,"
			+ " ProjectParticipantSdRole psdr "
			+ " WHERE r.agreementBetweenParticipantsId = ?1"
			+ " AND r.projectParticipantSdRoleIdIndividual = psdr.projectParticipantSdRoleId "
			+ " AND psdr.projectParticipantId = ?2 "
			+ " AND ((r.startDate <= now()) and ((r.endDate is null) or (r.endDate >= now())))")
	List<VRemunerationRateUpline> getRatesUplineCurrent(Long agreementBetweenParticipantsId, Long projectParticipantId);

	@Query("SELECT r FROM RemunerationRateUpline r WHERE r.remunerationRateUplineId = ?1")
	RemunerationRateUpline findByRemunerationRateUplineId(Long remunerationRateUplineId);


}


