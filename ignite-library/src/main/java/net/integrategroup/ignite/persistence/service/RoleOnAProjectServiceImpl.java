package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.RoleOnAProject;
import net.integrategroup.ignite.persistence.model.VRoleOnAProject;
import net.integrategroup.ignite.persistence.repository.RoleOnAProjectRepository;

@Service
public class RoleOnAProjectServiceImpl implements RoleOnAProjectService {

	@Autowired
	RoleOnAProjectRepository roleOnAProjectRepository;

	@Autowired
	DatabaseService databaseService;

	@Override
	public List<RoleOnAProject> findAll() {
		return roleOnAProjectRepository.findAll();
	}

	@Override
	public List<VRoleOnAProject> getAllRoleOnAProjectFromView() {
		return roleOnAProjectRepository.getAllRoleOnAProjectFromView();
	}


	@Override								//List from View that needs parameter
	public List<VRoleOnAProject> findListVRoleOnAProjectForServiceDiscipline(Long serviceDisciplineId) {
		return roleOnAProjectRepository.findListVRoleOnAProjectForServiceDiscipline(serviceDisciplineId);
	}



	@Override
	public List<RoleOnAProject> getList() {
		return roleOnAProjectRepository.findAll();
	}

	@Override
	public RoleOnAProject findByRoleOnAProjectId(Long roleOnAProjectId) {
		return roleOnAProjectRepository.findByRoleOnAProjectId(roleOnAProjectId);
	}

	@Override
	public RoleOnAProject save(RoleOnAProject roleOnAProject) {
		return roleOnAProjectRepository.save(roleOnAProject);
	}

	@Override
	public List<RoleOnAProject> findRoleOnAProjectNotLinked(Long genProcedureId) {
		return roleOnAProjectRepository.findRoleOnAProjectNotLinked(genProcedureId);
	}

	@Override
	public List<RoleOnAProject> findSdGroupRolesUsedNotLinked(Long serviceDisciplineId, Long serviceDisciplineId2) {
		return roleOnAProjectRepository.findSdGroupRolesUsedNotLinked(serviceDisciplineId, serviceDisciplineId2);
	}





	@Override
	public List<RoleOnAProject> findRolesForSdIndustry(Long serviceDisciplineId) {
		return roleOnAProjectRepository.findRolesForSdIndustry(serviceDisciplineId);
	}

	@Override
	public List<RoleOnAProject> findRolesForSd(Long serviceDisciplineId) {
		return roleOnAProjectRepository.findRolesForSd(serviceDisciplineId);
	}

	@Override
	public List<RoleOnAProject> findRolesForSdAvailable(Long serviceDisciplineId, Long serviceDisciplineIdGroup) {
		return roleOnAProjectRepository.findRolesForSdAvailable(serviceDisciplineId, serviceDisciplineIdGroup);
	}

}
