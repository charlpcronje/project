package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.AssignmentGroup;
import net.integrategroup.ignite.persistence.model.Project;
import net.integrategroup.ignite.persistence.repository.AssignmentGroupRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class AssignmentGroupServiceImpl implements AssignmentGroupService {

	@Autowired
	AssignmentGroupRepository assignmentGroupRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public List<AssignmentGroup> findAll() {
		return assignmentGroupRepository.findAll();
	}

	@Override
	public AssignmentGroup findByAssignmentGroupId(Long assignmentGroupId) {
		return assignmentGroupRepository.findByAssignmentGroupId(assignmentGroupId);
	}

	@Override
	public AssignmentGroup save(AssignmentGroup assignmentGroup) {
		return assignmentGroupRepository.save(assignmentGroup);
	}

	@Override
	public List<AssignmentGroup> findAssignmentGroupsPerInd(Long individualId) {
		return assignmentGroupRepository.findAssignmentGroupsPerInd(individualId);
	}

	@Override
	public List<Project> findProjectsPerInd(Long individualId) {
		return assignmentGroupRepository.findProjectsPerInd(individualId);
	}

}
