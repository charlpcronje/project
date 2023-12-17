package net.integrategroup.ignite.persistence.service;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;  //NB! This logger!

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ContactPoint;
import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.IndividualCompetency;
import net.integrategroup.ignite.persistence.model.Participant;
import net.integrategroup.ignite.persistence.model.ParticipantOffice;
import net.integrategroup.ignite.persistence.model.SystemMember;
import net.integrategroup.ignite.persistence.model.VIndividual;
import net.integrategroup.ignite.persistence.repository.IndividualRepository;

@Service
public class IndividualServiceImpl implements IndividualService {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	DatabaseService databaseService;

	@Autowired
	IndividualRepository individualRepository;

	@Override
	public List<Individual> findAll() {
		return individualRepository.findAll();
	}

	@Override
	public List<VIndividual> findAllIndividualsInView() {
		return individualRepository.findAllIndividualsInView();
	}

	@Override
	public Individual save(Individual individual) {
		return individualRepository.save(individual);
	}

	@Override
	public Individual findByIndividualId(Long individualId) {
		return individualRepository.findByIndividualId(individualId);
	}

	@Override
	public Individual findByUsername(String username) {
		return individualRepository.findByUsername(username);
	}

	@Override
	public Individual findByPasswordResetToken(String token) {
		return individualRepository.findByPasswordResetToken(token);
	}

	@Override
	public Integer getUsernameUsageCount(String username) {
		return individualRepository.getUsernameUsageCount(username);
	}

	@Override
	public Integer getUsernameUsageCount(Long individualId, String username) {
		return individualRepository.getUsernameUsageCount(individualId, username);
	}


	@Override
	public List<IndividualCompetency> getIndividualCompetencyRoles(Long individualId, Long serviceDisciplineId){
		return individualRepository.getIndividualCompetencyRoles(individualId, serviceDisciplineId);
	}

//	@Override
//	public List<V_ServiceDiscipline> getIndividualSdLevel0(){
//		return individualRepository.getIndividualSdLevel0();
//	}


	@Override
	public Participant getParticipantForIndividual(Long individualId) {
		return individualRepository.getParticipantForIndividual(individualId);
	}

	@Override
	public SystemMember getSystemMemberForIndividual(Long individualId) {
		return individualRepository.getSystemMemberForIndividual(individualId);
	}

	@Override
	public ContactPoint getContactPointForIndividual(Long individualId) {
		return individualRepository.getContactPointForIndividual(individualId);
	}

	@Override
	public List<Individual> getInvalidIndividuals() {
		return individualRepository.getInvalidIndividuals();
	}

	@Override
	public List<ParticipantOffice> getParticipantOfficesForIndividual(Long individualId) {
		return individualRepository.getParticipantOfficesForIndividual(individualId);
	}


	@Override
	public Individual getLoggedIndividualId(String userNameLoggedIn) {
		return individualRepository.getLoggedIndividualId(userNameLoggedIn);
	}

	@Override
	public void deleteIndividualSd(Long individualSdId) throws SQLException{

		String sql = "CALL ig_db.deleteIndividualSd(?);";

		logger.info("CALL ig_db.deleteIndividualSd(" + individualSdId + ");");


		// Logger levels: Can set level on Production server
		// Locally set to debug
		// Production typically set to Info or Severe
		// Set in catalina.conf

		// Lowest level: (debug)
		// logger.debug  :Writes to catalina.out
		// logger.info
		// logger.severe

		try {	//**//					
			Object[] params = {		
				individualSdId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			// return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			//return ResponseEntity.badRequest().body(e.getMessage());
		}

	}


	//@Override
	//public List<Individual> findIndividualsNotSelectedYet(Long participantId) {
	//	return individualRepository.findIndividualsNotSelectedYet(participantId);
	//}


	// Hierdie moet in die Controller wees...
	//@Override
	//public void delete(Long individualId, String username) throws SQLException {
	//	String sql = "CALL ig_db.deleteIndividual(?);";

	//	try (CallableStatement cstm = databaseService.prepareCall(sql)) {
	//		// Todo: Create an audit record to track who deleted the Individual
	//		// cstm.setLong(1, jsonString);
	//		// cstm.setString(2, securityUtils.getUsername());
	//		// cstm.registerOutParameter(3, java.sql.Types.BIGINT);
	//		cstm.setLong(1, individualId);
	//		//cstm.setString(2, username);
	//		cstm.execute();
	//	}
	//}
}
