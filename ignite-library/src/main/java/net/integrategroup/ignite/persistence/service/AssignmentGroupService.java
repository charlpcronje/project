package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.AssignmentGroup;
import net.integrategroup.ignite.persistence.model.Project;

public interface AssignmentGroupService {

	List<AssignmentGroup> findAll();

	AssignmentGroup findByAssignmentGroupId(Long assignmentGroupId);

	AssignmentGroup save(AssignmentGroup assignmentGroup);

	List<AssignmentGroup> findAssignmentGroupsPerInd(Long individualId);

	List<Project> findProjectsPerInd(Long individualId);


	//void delete(AssignmentGroup assignmentGroup);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
