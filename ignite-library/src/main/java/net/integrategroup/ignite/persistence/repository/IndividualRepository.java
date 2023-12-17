package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ContactPoint;
import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.IndividualCompetency;
import net.integrategroup.ignite.persistence.model.Participant;
import net.integrategroup.ignite.persistence.model.ParticipantOffice;
import net.integrategroup.ignite.persistence.model.SystemMember;
import net.integrategroup.ignite.persistence.model.VIndividual;

@Repository
public interface IndividualRepository extends CrudRepository<Individual, Long> {

	@Override
	List<Individual> findAll();

	@Query("SELECT iv FROM VIndividual iv")
	List<VIndividual> findAllIndividualsInView();


	@Query("SELECT " + "    i " + "  FROM " + "    Individual i " + "  WHERE " + "    i.individualId = ?1")
	Individual findByIndividualId(Long individualId);

	@Query("SELECT " + "    i " + "  FROM " + "    Individual i " + "  WHERE " + "    i.userName = ?1")
	Individual findByUsername(String username);

	@Query("SELECT " + "    i " + "  FROM " + "    Individual i " + "  WHERE " + "    i.passwordResetToken = ?1")
	Individual findByPasswordResetToken(String token);

	@Query("SELECT" + "    COUNT(1) AS usernameCount" + "  FROM" + "    Individual i" + "  WHERE"
			+ "    TRIM(LOWER(i.userName)) = TRIM(LOWER(?1))")
	Integer getUsernameUsageCount(String username);

	@Query("SELECT" + "    COUNT(1) AS usernameCount" + "  FROM" + "    Individual i" + "  WHERE"
			+ "    individualId <> ?1" + "    AND TRIM(LOWER(i.userName)) = TRIM(LOWER(?2))")
	Integer getUsernameUsageCount(Long individualId, String username);

	@Query("SELECT ic FROM IndividualCompetency ic"
			+ " LEFT JOIN IndividualSdRole isd "
			+ " ON ic.individualSdRoleId = isd.individualSdRoleId"
			+ " LEFT JOIN SdRole sd"
			+ " ON isd.sdRoleId = sd.sdRoleId"
			+ " WHERE 	isd.individualId = ?1 "
			+ " AND 	sd.serviceDisciplineId = ?2")
	List<IndividualCompetency> getIndividualCompetencyRoles(Long individualId, Long serviceDisciplineId);



	@Query("SELECT" + "    p" + "  FROM" + "    Individual i, Participant p" + "  WHERE"
			+ "    i.individualId = ?1" + "    AND p.isIndividual= 'Y'" + "    "
			+ "    AND p.participantId = i.participantId")
	Participant getParticipantForIndividual(Long individualId);

	@Query("SELECT" + "    sm" + "  FROM" + "    Individual i, SystemMember sm " + "  WHERE"
			+ "    i.individualId = ?1" + "    AND sm.individualId = i.individualId")
	SystemMember getSystemMemberForIndividual(Long individualId);

	@Query("SELECT cp " + "  FROM" + "    ContactPoint cp, Individual i " + "  WHERE"
			+ "    i.individualId = ?1 AND" + "    cp.contactPointId = cp.contactPointId")
	ContactPoint getContactPointForIndividual(Long individualId);

	@Query("SELECT" + "    i" + "  FROM " + "    Individual i" + "  WHERE"
			+ "    i.individualId NOT IN (SELECT sm.individualId FROM SystemMember sm)")
	List<Individual> getInvalidIndividuals();

	@Query("SELECT" + "    po" + "  FROM" + "    Individual i, ParticipantOffice po " + "  WHERE"
			+ "    i.individualId = ?1" + "    AND i.participantId = po.participantId")
	List<ParticipantOffice> getParticipantOfficesForIndividual(Long individualId);

	@Query("SELECT" + "    i" + "  FROM" + "    Individual i"
			+ "  WHERE i.userName = ?1")
	Individual getLoggedIndividualId(String userNameLoggedIn);

}
