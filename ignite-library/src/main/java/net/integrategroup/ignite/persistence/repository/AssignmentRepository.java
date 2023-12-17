package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Assignment;
import net.integrategroup.ignite.persistence.model.Task;
import net.integrategroup.ignite.persistence.model.VAssignmentForIndividualList;
import net.integrategroup.ignite.persistence.model.VAssignmentList;
import net.integrategroup.ignite.persistence.model.VAssignmentListNewSubs;
import net.integrategroup.ignite.persistence.model.VProjectParticipant;


@Repository
public interface AssignmentRepository extends CrudRepository<Assignment, Long> {

	@Override
	List<Assignment> findAll();

	@Query("SELECT a FROM Assignment a WHERE a.assignmentId = ?1")
	Assignment findByAssignmentId(Long assignmentId);


	@Query("SELECT a FROM VAssignmentList a "
			+ " WHERE a.startDate BETWEEN ?1 AND ?2")
	List<VAssignmentList> getAllAssignments(Date firstDay, Date lastDay);

	@Query("SELECT a FROM VAssignmentList a WHERE a.completed = 'N' "
			+ " AND a.startDate BETWEEN ?1 AND ?2")
	List<VAssignmentList> getAllCurrentAssignments(Date firstDay, Date lastDay);

	@Query("SELECT a FROM VAssignmentListNewSubs a "
			+ " WHERE a.startDate BETWEEN ?1 AND ?2")
	List<VAssignmentListNewSubs> getAllCurrentAssignmentsWithNewSubs(Date firstDay, Date lastDay);


	@Query("SELECT ppv FROM VProjectParticipant ppv WHERE ppv.projectId = ?1")
	List<VProjectParticipant> findAllInPPViewForProjectId(Long projectId);

	@Query("SELECT ppv FROM VProjectParticipant ppv WHERE ppv.projectId = ?1 and ppv.isIndividual = 'Y'")
	List<VProjectParticipant> findAllInPPViewForProjectIdIndiv(Long projectId);

	@Query("SELECT t FROM Task t WHERE t.assignmentId = ?1")
	List<Task> findAllTasksForAssignment(Long assignmentId);

	@Query("SELECT ppv FROM VAssignmentForIndividualList ppv WHERE ppv.individualId = ?1")
	List<VAssignmentForIndividualList> findAllAssignmentsForIndividual(Long individualId);

	@Query("SELECT ppv FROM VAssignmentForIndividualList ppv WHERE ppv.individualId = ?1 and ppv.completed <> 'Y'")
	List<VAssignmentForIndividualList> findAllAssignmentsForIndividualCurrent(Long individualId);

	@Query("SELECT max(a.assignmentNumber) + 1 FROM Assignment a, ProjectParticipant p WHERE a.projectParticipantIdInstructor = p.projectParticipantId and p.projectId = ?1")
	Integer getNextAssignmentNumber(Long projectId);
}


