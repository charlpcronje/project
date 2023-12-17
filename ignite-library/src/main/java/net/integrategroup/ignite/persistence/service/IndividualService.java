package net.integrategroup.ignite.persistence.service;

import java.sql.SQLException;
import java.util.List;

import net.integrategroup.ignite.persistence.model.ContactPoint;
import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.IndividualCompetency;
import net.integrategroup.ignite.persistence.model.Participant;
import net.integrategroup.ignite.persistence.model.ParticipantOffice;
import net.integrategroup.ignite.persistence.model.SystemMember;
import net.integrategroup.ignite.persistence.model.VIndividual;

public interface IndividualService {

	List<Individual> findAll();

	List<VIndividual> findAllIndividualsInView();

	Individual save(Individual individual);

	Individual findByIndividualId(Long individualId);

	Individual findByUsername(String username);

	Individual findByPasswordResetToken(String token);

	Integer getUsernameUsageCount(String username);

	Integer getUsernameUsageCount(Long individualId, String username);

	Participant getParticipantForIndividual(Long individualId);

	SystemMember getSystemMemberForIndividual(Long individualId);

	ContactPoint getContactPointForIndividual(Long individualId);

	List<Individual> getInvalidIndividuals();

	List<ParticipantOffice> getParticipantOfficesForIndividual(Long individualId);

	List<IndividualCompetency> getIndividualCompetencyRoles(Long individualId, Long serviceDisciplineId);

	Individual getLoggedIndividualId(String userNameLoggedIn);

	void deleteIndividualSd(Long individualSdId) throws SQLException;


//	List<V_ServiceDiscipline> getIndividualSdLevel0();



	//List<Individual> findIndividualsNotSelectedYet(Long participantId);

	//void delete(Long individualId, String username) throws SQLException;


}
