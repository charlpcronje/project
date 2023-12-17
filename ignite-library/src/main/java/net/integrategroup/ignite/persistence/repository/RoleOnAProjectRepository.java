package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.RoleOnAProject;
import net.integrategroup.ignite.persistence.model.VRoleOnAProject;

@Repository
public interface RoleOnAProjectRepository extends CrudRepository<RoleOnAProject, Long> {

	@Override
	List<RoleOnAProject> findAll();

	@Query("SELECT rop FROM VRoleOnAProject rop order by serviceDisciplineIdIndustry_Name, name")
	List<VRoleOnAProject> getAllRoleOnAProjectFromView();

	@Query("SELECT v FROM VRoleOnAProject v WHERE v.serviceDisciplineIdIndustry = ?1")				//List from View that needs parameter
	List<VRoleOnAProject> findListVRoleOnAProjectForServiceDiscipline(Long serviceDisciplineId);

	@Query("SELECT roap FROM RoleOnAProject roap WHERE roap.roleOnAProjectId = ?1")
	RoleOnAProject findByRoleOnAProjectId(Long roleOnAProjectId);

	@Query("SELECT a FROM RoleOnAProject a WHERE roleOnAProjectId not in (Select roleOnAProjectId from PersonResponsible where genProcedureId = ?1)")
	List<RoleOnAProject> findRoleOnAProjectNotLinked(Long genProcedureId);

	@Query("SELECT a FROM RoleOnAProject a WHERE serviceDisciplineIdIndustry = ?1 AND roleOnAProjectId not in (SELECT roleOnAProjectId FROM SdRole WHERE ServiceDisciplineId = ?2)")
	List<RoleOnAProject> findSdGroupRolesUsedNotLinked(Long serviceDisciplineId, Long serviceDisciplineId2);

	@Query("SELECT a FROM RoleOnAProject a WHERE serviceDisciplineIdIndustry = ?1")
	List<RoleOnAProject> findRolesForSdIndustry(Long serviceDisciplineId);

	@Query("SELECT r FROM RoleOnAProject r "
			+ " LEFT JOIN SdRole sd"
			+ " ON (r.roleOnAProjectId = sd.roleOnAProjectId)"
			+ " WHERE sd.serviceDisciplineId = ?1")
	List<RoleOnAProject> findRolesForSd(Long serviceDisciplineId);

	@Query("SELECT r FROM RoleOnAProject r "
			+ " WHERE r.serviceDisciplineIdIndustry = ?2"
			+ " AND  r.roleOnAProjectId NOT IN (Select sd.roleOnAProjectId FROM SdRole sd WHERE sd.serviceDisciplineId = ?1)"
			+ " ")
	List<RoleOnAProject> findRolesForSdAvailable(Long serviceDisciplineId, Long serviceDisciplineIdIndustry);

}


