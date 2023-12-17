package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.RoleOnAProject;
import net.integrategroup.ignite.persistence.model.SdRole;
import net.integrategroup.ignite.persistence.model.VSdRole;
import net.integrategroup.ignite.persistence.repository.SdRoleRepository;

@Service
public class SdRoleServiceImpl implements SdRoleService {

	@Autowired
	SdRoleRepository sdRoleRepository;

	@Override
	public List<SdRole> findAll() {
		return sdRoleRepository.findAll();
	}

	@Override
	public List<VSdRole> findAllVSdRole() {
		return sdRoleRepository.findAllVSdRole();
	}

	@Override
	public List<SdRole> findByServiceDisciplineId(Long serviceDisciplineId) {
		return sdRoleRepository.findByServiceDisciplineId(serviceDisciplineId);
	}

	@Override
	public SdRole save(SdRole sdRole) {
		return sdRoleRepository.save(sdRole);
	}

	@Override
	public SdRole findBySdRoleId(Long sdRoleId) {
		return sdRoleRepository.findBySdRoleId(sdRoleId);
	}

	@Override
	public List<RoleOnAProject> findRolesByProjectSdId(Long projectSdId) {
		return sdRoleRepository.findRolesByProjectSdId(projectSdId);
	}

	@Override
	public List<RoleOnAProject> findRolesAvailableByProjectSdId(Long projectSdId, Long projectParticipantId) {
		return sdRoleRepository.findRolesAvailableByProjectSdId(projectSdId, projectParticipantId);
	}

}
