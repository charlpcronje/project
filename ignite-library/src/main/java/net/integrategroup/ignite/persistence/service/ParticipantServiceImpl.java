package net.integrategroup.ignite.persistence.service;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Participant;
import net.integrategroup.ignite.persistence.model.ParticipantOffice;
import net.integrategroup.ignite.persistence.model.VParticipant;
import net.integrategroup.ignite.persistence.model.VParticipantMin;
import net.integrategroup.ignite.persistence.model.VParticipantResource;
import net.integrategroup.ignite.persistence.repository.ParticipantRepository;

@Service
public class ParticipantServiceImpl implements ParticipantService {

	@Autowired
	ParticipantRepository participantRepository;

	@Autowired
	DatabaseService databaseService;

	@Override
	public List<Participant> findAll() {
		return participantRepository.findAll();
	}

	
	@Override
	public List<VParticipantMin> findParticipantOnlyMe(Long participantId) {    //me, and Participants linked to me thru projects
		return participantRepository.findParticipantOnlyMe(participantId);
	}	

	@Override
	public List<VParticipantMin> findParticipantOnlyMeFinancial(Long participantId) {    //me, and Participants where I am Representative or Marketing
		return participantRepository.findParticipantOnlyMeFinancial(participantId);
	}		
	
	
	
	@Override
	public List<VParticipantMin> findAllParticipantsInView() {                 //all
		return participantRepository.findAllParticipantsInView();
	}


	@Override
	public List<VParticipantMin> findAllIndividualParticipantsInViewOnlyMe(Long participantId) {    //me
		return participantRepository.findAllIndividualParticipantsInViewOnlyMe(participantId);
	}
	
	@Override
	public List<VParticipantMin> findAllIndividualParticipantsInView() {                            //all
		return participantRepository.findAllIndividualParticipantsInView();
	}

	
	
	
	@Override
	public VParticipant findInViewByParticipantId(Long participantId) {
		return participantRepository.findInViewByParticipantId(participantId);
	}


	@Override
	public Participant save(Participant participant) {
		return participantRepository.save(participant);
	}

	@Override
	public Participant findByParticipantId(Long participantId) {
		return participantRepository.findByParticipantId(participantId);

	}

	@Override
	public List<VParticipantMin> findAllNonIndividualParticipants() {
		return participantRepository.findAllNonIndividualParticipants();
	}

	@Override
	public List<Participant> getOrphanParticipants() {
		return participantRepository.getOrphanParticipants();
	}

	@Override
	public List<VParticipantResource> findParticipantResources(Long participantId) {
		return participantRepository.findParticipantResources(participantId);
	}

	@Override
	public void delete(Participant participant) {
		participantRepository.delete(participant);
	}

	@Override
	public List<ParticipantOffice> getOfficesForParticipant(Long participantId) {
		return participantRepository.getOfficesForParticipant(participantId);
	}

	@Override
	public List<VParticipantMin> getRepesentativeOf(Long individualId) {
		return participantRepository.getRepesentativeOf(individualId);
	}

	@Override
	public List<VParticipantMin> getMarketingRepOf(Long individualId) {
		return participantRepository.getMarketingRepOf(individualId);
	}

	@Override
	public void clearLogo(Long participantId) throws SQLException {
		String sql = "call ig_db.clearParticipantLogo(?);";

		try {	//**//					
			Object[] params = {		
				participantId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			//**//
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
