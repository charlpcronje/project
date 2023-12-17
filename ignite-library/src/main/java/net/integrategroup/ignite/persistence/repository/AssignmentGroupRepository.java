package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.AssignmentGroup;
import net.integrategroup.ignite.persistence.model.Project;

@Repository
public interface AssignmentGroupRepository extends CrudRepository<AssignmentGroup, Long> {

	@Override
	List<AssignmentGroup> findAll();

	@Query("SELECT a FROM AssignmentGroup a WHERE assignmentGroupId = ?1")
	AssignmentGroup findByAssignmentGroupId(Long assignmentGroupId);

//	@Query("SELECT a FROM AssignmentGroup a JOIN project pr ON a.projectId = pr.projectId JOIN ProjectParticipant pp ON pr.projectId = pp.projectId JOIN Participant p ON pp.participantId = p.participantId JOIN individual i ON p.participantId = i.participantId WHERE i.individualId = ?1")
//	List<AssignmentGroup> findAssignmentGroupsPerInd(Long individualId);

	@Query("SELECT a FROM AssignmentGroup a, Project pr, ProjectParticipant pp, Participant p, "
			+ "Individual i	WHERE a.projectId = pr.projectId AND pr.projectId = pp.projectId AND "
			+ "pp.participantId = p.participantId AND p.participantId = i.participantId AND i.individualId = ?1")
	List<AssignmentGroup> findAssignmentGroupsPerInd(Long individualId);

	@Query("SELECT pr FROM Project pr, "
			+ "ProjectParticipant pp, Participant p, Individual i "
			+ "WHERE pr.projectId = pp.projectId "
			+ "AND pp.participantId = p.participantId "
			+ "AND p.participantId = i.participantId "
			+ "AND i.individualId = ?1")
	List<Project> findProjectsPerInd(Long individualId);
}
