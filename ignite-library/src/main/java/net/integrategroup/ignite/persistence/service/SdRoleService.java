package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.RoleOnAProject;
import net.integrategroup.ignite.persistence.model.SdRole;
import net.integrategroup.ignite.persistence.model.VSdRole;

public interface SdRoleService {

	List<SdRole> findAll();

	List<VSdRole> findAllVSdRole();

	List<SdRole> findByServiceDisciplineId(Long serviceDisciplineId);

	SdRole save(SdRole sdRole);

	SdRole findBySdRoleId(Long sdRoleId);

	List<RoleOnAProject> findRolesByProjectSdId(Long projectSdId);

	List<RoleOnAProject> findRolesAvailableByProjectSdId(Long projectSdId, Long projectParticipantId);

}
