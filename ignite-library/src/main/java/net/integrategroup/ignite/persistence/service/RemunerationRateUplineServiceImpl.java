package net.integrategroup.ignite.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.RemunerationRateUpline;
import net.integrategroup.ignite.persistence.model.VAgreementParticipants;
import net.integrategroup.ignite.persistence.model.VPPIndividualRatesUpline;
import net.integrategroup.ignite.persistence.model.VRemunerationRateUpline;
import net.integrategroup.ignite.persistence.repository.RemunerationRateUplineRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

/**
 * @author Ingrid Marais
 *
 */
@Service
public class RemunerationRateUplineServiceImpl implements RemunerationRateUplineService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	RemunerationRateUplineRepository remunerationRateUplineRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public List<VAgreementParticipants> getPPIndividualListWithTimeCostAgreements(Long projectId){
		return remunerationRateUplineRepository.getPPIndividualListWithTimeCostAgreements(projectId);
	}

	@Override
	public List<VAgreementParticipants> getAgreementIndividualList(Long agreementBetweenParticipantsId){
		return remunerationRateUplineRepository.getAgreementIndividualList(agreementBetweenParticipantsId);
	}

	@Override
	public List<VAgreementParticipants> getAgreementParticipantList(Long agreementBetweenParticipantsId){
		return remunerationRateUplineRepository.getAgreementParticipantList(agreementBetweenParticipantsId);
	}

	@Override
	public List<VRemunerationRateUpline> getRatesUpline(Long agreementBetweenParticipantsId, Long projectParticipantId){
		return remunerationRateUplineRepository.getRatesUpline(agreementBetweenParticipantsId, projectParticipantId);
	}

	@Override
	public List<VRemunerationRateUpline> getRatesUplineCurrent(Long agreementBetweenParticipantsId, Long projectParticipantId){
		return remunerationRateUplineRepository.getRatesUplineCurrent(agreementBetweenParticipantsId, projectParticipantId);
	}

	//	@Override
//	public RemunerationRateUpline save(RemunerationRateUpline remunerationRateUpline) {
//		return remunerationRateUplineRepository.save(remunerationRateUpline);
//	}


//	@Override
//	public RemunerationRateUpline save( Long remunerationRateUplineId, 			//1
//										Long agreementBetweenParticipantsId,	//2
//										Long projectParticipantId,				//3
//										Long participantIdIndividual,			//4
//										Long projBasedRemunTypeId,			//5
//										Long expenseTypeId,						//6
//										Double expenseHandlingPerc,				//7
//										Double ratePerUnit,						//8
//										String description,						//9
//										Date startDate, 						//10
//										Date endDate							//11
//										) throws SQLException {
//
//		Connection con = ((SessionImpl) entityManager.getDelegate()).connection();
//		CallableStatement stmt = con.prepareCall("{call ig_db.saveRemunerationRateUpline(?,?,?,?,?,?,?,?,?,?,?,?)}");
//
//		// Check for null values
//		if (remunerationRateUplineId == null) {
//			stmt.setNull(1, Types.BIGINT);
//		} else {
//			stmt.setLong(1, remunerationRateUplineId);
//		}
//
//		stmt.setLong(2, agreementBetweenParticipantsId);
//		stmt.setLong(3, projectParticipantId);
//		stmt.setLong(4, participantIdIndividual);
//
//		if (projBasedRemunTypeId == null) {
//			stmt.setNull(5, Types.VARCHAR);
//		} else {
//			stmt.setString(5, projBasedRemunTypeId);
//		}
//
//		if (expenseTypeId == null) {
//			stmt.setNull(6, Types.BIGINT);
//		} else {
//			stmt.setLong(6, expenseTypeId);
//		}
//
//		if (expenseHandlingPerc == null) {
//			stmt.setNull(7, Types.DECIMAL);
//		} else {
//			stmt.setDouble(7, expenseHandlingPerc);
//		}
//
//		if (ratePerUnit == null) {
//			stmt.setNull(8, Types.DECIMAL);
//		} else {
//			stmt.setDouble(8, ratePerUnit);
//		}
//
//		if (description == null) {
//			stmt.setNull(9, Types.VARCHAR);
//		} else {
//			stmt.setString(9, description);
//		}
//		stmt.setDate(10, new java.sql.Date(startDate.getTime()));
//
//		if (endDate == null) {
//			stmt.setNull(11, Types.DATE);
//		} else {
//			stmt.setDate(11, new java.sql.Date(endDate.getTime()));
//		}
//
//		stmt.setString(12, securityUtils.getUsername());
//		stmt.execute();
//
//		return remunerationRateUplineRepository.findByRemunerationRateUplineId(remunerationRateUplineId);
//	}


	@Override
	public RemunerationRateUpline findByRemunerationRateUplineId(Long remunerationRateUplineId) {
		return remunerationRateUplineRepository.findByRemunerationRateUplineId(remunerationRateUplineId);
	}

//	@Override
//	public Integer getUniqueDateRange(	Long remunerationRateUplineId,
//										Long agreementBetweenParticipantsId,
//										Long projectParticipantId,
//										Long participantIdIndividual,
//										Long projBasedRemunTypeId,
//										Date startDate,
//										Date endDate) {
//		return remunerationRateUplineRepository.getUniqueDateRange(remunerationRateUplineId,agreementBetweenParticipantsId,projectParticipantId,participantIdIndividual,projBasedRemunTypeId,startDate, endDate);
//	}



//	@Override
//	public List<Project> findAll() {
//		return projectRepository.findAll();
//	}
//
//
//
//
//	@Override
//	public List<V_Project> getTree() {
//		return projectRepository.findTopLevel();
//	}
//
//
//	@Override
//	public Project findByProjectId(Long projectId) {
//		return projectRepository.findByProjectId(projectId);
//	}
//
//	@Override
//	public V_Project findProjectViewByProjectId(Long projectId) {
//		return projectRepository.findProjectViewByProjectId(projectId);
//	}
//
//	@Override
//	public List<V_ProjectList> findAllProjectsInView() {
//		return projectRepository.findAllProjectsInView();
//	}
//
//	@Override
//	public void delete(Long projectId, String username) throws SQLException {
//		String sql = "CALL ig_db.deleteProject(?,?);";
//
//		try (CallableStatement cstm = databaseService.prepareCall(sql)) {
//			// Todo: Create an audit record to track who deleted the Project
//			// cstm.setLong(1, jsonString);
//			// cstm.setString(2, securityUtils.getUsername());
//			// cstm.registerOutParameter(3, java.sql.Types.BIGINT);
//			cstm.setLong(1, projectId);
//			cstm.setString(2, username);
//			cstm.execute();
//		}
//	}
//
//	@Override
//	public V_ProjectList findProjectListViewByProjectId(Long projectId) {
//		return projectRepository.findProjectListViewByProjectId(projectId);
//	}
//
////	@Override
////	public List<ProjectOperationalServiceDisciplineView> findProjectOSDs(Long projectId) {
////		return projectRepository.findProjectOSDs(projectId);
////	}
//
//	@Override
//	public List<ProjectStage> findProjectStages(Long projectId) {
//		return projectRepository.findProjectStages(projectId);
//	}
//
//	@Override
//	public String getNextProjectNumber() {
//		return projectRepository.getNextProjectNumber();
//	}
//
//
////	@Override
////	public String getCurrentProjectStage(Long projectId) {
////		return projectRepository.getCurrentProjectStage(projectId);
////	}
////
//	@Override
//	public List<V_ProjectSd> findProjectSds(Long projectId){
//		return projectRepository.findProjectSds(projectId);
//	}
//
//	@Override
//	public List<V_ProjectSd> findProjectSdsNotSelectedForParticipant(Long projectId, Long projectParticipantId){
//		return projectRepository.findProjectSdsNotSelectedForParticipant(projectId, projectParticipantId);
//	}
//
//	@Override
//	public List<V_ServiceDiscipline> getProjectSdsNotSelected(Long projectId){
//		return projectRepository.getProjectSdsNotSelected(projectId);
//	}
//
//	@Override
//	public List<V_PPTree> getPPTree(Long projectId) {
//		return projectRepository.findTopLevelPPTree(projectId);
//	}
//
//	@Override
//	public List<V_PPTree> getPPTreeChildren(Long projectParticipantIdAboveMe) {
//		return projectRepository.getPPTreeChildren(projectParticipantIdAboveMe);
//	}
//

	@Override
	public List<VPPIndividualRatesUpline> getRatesUplineForTimesheetEntry(Long timesheetId){
		return remunerationRateUplineRepository.getRatesUplineForTimesheetEntry(timesheetId);
	}


}

