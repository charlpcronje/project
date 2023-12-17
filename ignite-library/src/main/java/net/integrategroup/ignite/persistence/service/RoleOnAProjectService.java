package net.integrategroup.ignite.persistence.service;

import java.util.List;

//import net.integrategroup.ignite.persistence.model.ContactPointView;
import net.integrategroup.ignite.persistence.model.RoleOnAProject;
import net.integrategroup.ignite.persistence.model.VRoleOnAProject;

public interface RoleOnAProjectService {

	List<RoleOnAProject> findAll();

	List<VRoleOnAProject> getAllRoleOnAProjectFromView();

	List<RoleOnAProject> getList();

	List<VRoleOnAProject> findListVRoleOnAProjectForServiceDiscipline(Long serviceDisciplineId);						//List from View that needs parameter



	RoleOnAProject findByRoleOnAProjectId(Long roleOnAProjectId);

	RoleOnAProject save(RoleOnAProject roleOnAProject);

	List<RoleOnAProject> findRoleOnAProjectNotLinked(Long genProcedureId);

	List<RoleOnAProject> findSdGroupRolesUsedNotLinked(Long serviceDisciplineId, Long serviceDisciplineId2);


	List<RoleOnAProject> findRolesForSdIndustry(Long serviceDisciplineId);

	List<RoleOnAProject> findRolesForSd(Long serviceDisciplineId);

	List<RoleOnAProject> findRolesForSdAvailable(Long serviceDisciplineId, Long serviceDisciplineIdGroup);

}
