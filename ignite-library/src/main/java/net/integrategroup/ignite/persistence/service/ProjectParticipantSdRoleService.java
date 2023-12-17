package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ProjectParticipantSdRole;
//import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;

public interface ProjectParticipantSdRoleService {

	ProjectParticipantSdRole save(ProjectParticipantSdRole projectParticipantSdRole);

	ProjectParticipantSdRole findByProjectParticipantSdRoleId(Long projectParticipantSdRoleId);

	List<ProjectParticipantSdRole> getProjectParticipantSdList(Long projectParticipantId);

//	List<VProjectParticipantSdRoles> getProjectParticipantSdRoleList(Long projectParticipantId);

}
