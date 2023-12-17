package net.integrategroup.ignite.persistence.service;

import java.sql.SQLException;
import java.util.List;

import net.integrategroup.ignite.persistence.model.Participant;
import net.integrategroup.ignite.persistence.model.ParticipantOffice;
import net.integrategroup.ignite.persistence.model.VParticipant;
import net.integrategroup.ignite.persistence.model.VParticipantMin;
import net.integrategroup.ignite.persistence.model.VParticipantResource;

public interface ParticipantService {

	List<Participant> findAll();

	List<VParticipantMin> findParticipantOnlyMe(Long participantId);  //me, and Participants linked to me thru projects
	
	List<VParticipantMin> findParticipantOnlyMeFinancial(Long participantId);  //me, and Participants where I am Representative or Marketing
	
	
	
	List<VParticipantMin> findAllParticipantsInView();                //all
	
	
	List<VParticipantMin> findAllIndividualParticipantsInViewOnlyMe(Long participantId);     //me
	
	List<VParticipantMin> findAllIndividualParticipantsInView();     //all
	
	

	VParticipant findInViewByParticipantId(Long participantId);

	Participant save(Participant participant);

	Participant findByParticipantId(Long participantId);

	List<VParticipantMin> findAllNonIndividualParticipants();

	List<Participant> getOrphanParticipants();

	List<VParticipantResource> findParticipantResources(Long participantId);

	void delete(Participant participantId);

	List<ParticipantOffice> getOfficesForParticipant(Long participantId);

	List<VParticipantMin> getRepesentativeOf(Long individualId);

	List<VParticipantMin> getMarketingRepOf(Long individualId);

	void clearLogo(Long participantId) throws SQLException;
}
