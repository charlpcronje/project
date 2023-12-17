package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ProjectParticipantSdRole;
//import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;
import net.integrategroup.ignite.persistence.repository.ProjectParticipantSdRoleRepository;

// @author Ingrid Marais

@Service
public class ProjectParticipantSdRoleServiceImpl implements ProjectParticipantSdRoleService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ProjectParticipantSdRoleRepository projectParticipantSdRepository;


	@Override
	public List<ProjectParticipantSdRole> getProjectParticipantSdList(Long projectParticipantId) {
		return projectParticipantSdRepository.getProjectParticipantSdList(projectParticipantId);
	}

//	@Override
//	public List<VProjectParticipantSdRoles> getProjectParticipantSdRoleList(Long projectParticipantId) {
//		return projectParticipantSdRepository.getProjectParticipantSdRoleList(projectParticipantId);
//	}
//
	@Override
	public ProjectParticipantSdRole save(ProjectParticipantSdRole projectParticipantSd) {
		return projectParticipantSdRepository.save(projectParticipantSd);
	}

	@Override
	public ProjectParticipantSdRole findByProjectParticipantSdRoleId(Long projectParticipantSdRoleId) {
		return projectParticipantSdRepository.findByProjectParticipantSdRoleId(projectParticipantSdRoleId);
	}



}
