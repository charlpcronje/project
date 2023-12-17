package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Participant;
import net.integrategroup.ignite.persistence.model.ParticipantOffice;
import net.integrategroup.ignite.persistence.model.VParticipant;
import net.integrategroup.ignite.persistence.model.VParticipantMin;
import net.integrategroup.ignite.persistence.model.VParticipantResource;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long> {

	@Override

	List<Participant> findAll();
	

	
	@Query("Select vp from VParticipantMin vp where ParticipantId in (Select participantId from ProjectParticipant where projectId in "
			+ "(Select projectId from ProjectParticipant where participantId = ?1))")    //me, and Participants linked to me thru projects
	List<VParticipantMin> findParticipantOnlyMe(Long participantId);	
	
	@Query("Select vp from VParticipantMin vp"
			+ " where participantId  = ?1"
			+ " or participantId in (Select participantId from Participant where representativeIndividualId = "
			+ "    (select individualId from Individual where participantId = ?1))"
			+ " or ParticipantId in (Select participantId from Participant where marketingIndividualId = "
			+ "    (select individualId from Individual where participantId = ?1))")    //me, and Participants where I am Representative or Marketing
	List<VParticipantMin> findParticipantOnlyMeFinancial(Long participantId);	
	
	
	
	
	@Query("SELECT vp FROM VParticipantMin vp")				                             //all
	List<VParticipantMin> findAllParticipantsInView();	
	
	
	@Query("SELECT vp FROM VParticipantMin vp WHERE vp.isIndividual = 'Y' and vp.participantId = ?1")   //me
	List<VParticipantMin> findAllIndividualParticipantsInViewOnlyMe(Long participantId);	
	
	@Query("SELECT vp FROM VParticipantMin vp WHERE vp.isIndividual = 'Y'")    //all
	List<VParticipantMin> findAllIndividualParticipantsInView();

	
	
	
	
	@Query("SELECT vp FROM VParticipant vp WHERE vp.participantId = ?1")
	VParticipant findInViewByParticipantId(Long id);


	@Query("SELECT p FROM Participant p WHERE p.participantId = ?1")
	Participant findByParticipantId(Long id);

	@Query("SELECT p FROM VParticipantMin p WHERE p.isIndividual <> 'Y'")
	List<VParticipantMin> findAllNonIndividualParticipants();

	@Query("SELECT p FROM Participant p WHERE p.isIndividual = 'Y'"
			+ "  AND participantId NOT IN (SELECT i.participantId FROM Individual i, SystemMember sm WHERE i.individualId = sm.individualId)")
	List<Participant> getOrphanParticipants();

	@Query("SELECT r  FROM VParticipantResource r WHERE r.participantId = ?1")
	List<VParticipantResource> findParticipantResources(Long participantId);

	@Query("SELECT po " + "  FROM ParticipantOffice po WHERE po.participantId = ?1")
	List<ParticipantOffice> getOfficesForParticipant(Long participantId);

	@Query("SELECT vp FROM VParticipantMin vp WHERE vp.representativeIndividualId = ?1")
	List<VParticipantMin> getRepesentativeOf(Long individualId);


	@Query("SELECT vp FROM VParticipantMin vp WHERE vp.marketingIndividualId = ?1")
	List<VParticipantMin> getMarketingRepOf(Long individualId);
}
