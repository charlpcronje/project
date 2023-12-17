package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Assignment;
import net.integrategroup.ignite.persistence.model.Task;
import net.integrategroup.ignite.persistence.model.VAssignmentForIndividualList;
import net.integrategroup.ignite.persistence.model.VAssignmentList;
import net.integrategroup.ignite.persistence.model.VAssignmentListNewSubs;
import net.integrategroup.ignite.persistence.model.VProjectParticipant;
import net.integrategroup.ignite.persistence.repository.AssignmentRepository;

// @author Johannes

@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	AssignmentRepository assignmentRepository;

	@Override
	public List<Assignment> findAll() {
		return assignmentRepository.findAll();
	}

	@Override
	public List<Task> findAllTasksForAssignment(Long assignmentId){
		return assignmentRepository.findAllTasksForAssignment(assignmentId);
	}


	@Override
	public Assignment save(Assignment assignment) {
		return assignmentRepository.save(assignment);
	}

//	@Override
//	public Assignment findByAssignmentId(Long assignmentId) {
//		return assignmentRepository.findByAssignmentId(assignmentId);
//	}
//
//	@Override
//	public void delete(Long assignmentId, String username) throws SQLException {
//		String sql = "CALL ig_db.deleteAssignment(?,?);";
//
//		try (CallableStatement cstm = databaseService.prepareCall(sql)) {
//			// Todo: Create an audit record to track who deleted the Assignment
//			// cstm.setLong(1, jsonString);
//			// cstm.setString(2, securityUtils.getUsername());
//			// cstm.registerOutParameter(3, java.sql.Types.BIGINT);
//			cstm.setLong(1, assignmentId);
//			cstm.setString(2, username);
//			cstm.execute();
//		}
//	}

	@Override
	public List<VAssignmentList> getAllAssignments(Date firstDay, Date lastDay) {
		return assignmentRepository.getAllAssignments(firstDay, lastDay);

	}

	@Override
	public List<VAssignmentList> getAllCurrentAssignments(Date firstDay, Date lastDay) {
		return assignmentRepository.getAllCurrentAssignments(firstDay, lastDay);

	}

	@Override
	public List<VAssignmentListNewSubs> getAllCurrentAssignmentsWithNewSubs(Date firstDay, Date lastDay) {
		return assignmentRepository.getAllCurrentAssignmentsWithNewSubs(firstDay, lastDay);

	}
	@Override
	public Assignment findByAssignmentId(Long assignmentId) {
		return assignmentRepository.findByAssignmentId(assignmentId);
	}

	@Override
	public List<VProjectParticipant> findAllInPPViewForProjectId(Long projectId){
		return assignmentRepository.findAllInPPViewForProjectId(projectId);
	}

	@Override
	public List<VProjectParticipant> findAllInPPViewForProjectIdIndiv(Long projectId){
		return assignmentRepository.findAllInPPViewForProjectIdIndiv(projectId);
	}

	@Override
	public List<VAssignmentForIndividualList> findAllAssignmentsForIndividual(Long individualId){
		return assignmentRepository.findAllAssignmentsForIndividual(individualId);
	}

	@Override
	public List<VAssignmentForIndividualList> findAllAssignmentsForIndividualCurrent(Long individualId){
		return assignmentRepository.findAllAssignmentsForIndividualCurrent(individualId);
	}

	@Override
	public Integer getNextAssignmentNumber(Long projectId) {
		return assignmentRepository.getNextAssignmentNumber(projectId);
	}

}

