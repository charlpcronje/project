package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.Assignment;
import net.integrategroup.ignite.persistence.model.Task;
import net.integrategroup.ignite.persistence.model.VAssignmentForIndividualList;
import net.integrategroup.ignite.persistence.model.VAssignmentList;
import net.integrategroup.ignite.persistence.model.VAssignmentListNewSubs;
import net.integrategroup.ignite.persistence.model.VProjectParticipant;

public interface AssignmentService {

	List<Assignment> findAll();

	Assignment save(Assignment assignment);

	List<VAssignmentList>  getAllAssignments(Date firstDay, Date lastDay);

	List<VAssignmentList>  getAllCurrentAssignments(Date firstDay, Date lastDay);

	List<VAssignmentListNewSubs>  getAllCurrentAssignmentsWithNewSubs(Date firstDay, Date lastDay);


	List<VProjectParticipant> findAllInPPViewForProjectId(Long projectId);

	List<VProjectParticipant> findAllInPPViewForProjectIdIndiv(Long projectId);

	List<VAssignmentForIndividualList> findAllAssignmentsForIndividual(Long individualId);

	List<VAssignmentForIndividualList> findAllAssignmentsForIndividualCurrent(Long individualId);

	List<Task> findAllTasksForAssignment(Long assignmentId);

	Assignment findByAssignmentId(Long assignmentId);

	Integer getNextAssignmentNumber(Long projectId);

}
