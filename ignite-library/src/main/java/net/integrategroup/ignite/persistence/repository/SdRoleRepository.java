package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.RoleOnAProject;
import net.integrategroup.ignite.persistence.model.SdRole;
import net.integrategroup.ignite.persistence.model.VSdRole;

@Repository
public interface SdRoleRepository extends CrudRepository<SdRole, Long> {

	@Override
	List<SdRole> findAll();

	@Query("SELECT vs FROM VSdRole vs")
	List<VSdRole> findAllVSdRole();


	@Query("SELECT b" +
			"  FROM " +
			"    SdRole b" +
			"  WHERE" +
			"    b.serviceDisciplineId = ?1")
	List<SdRole> findByServiceDisciplineId(Long serviceDisciplineId);


	@Query("SELECT b" +
			"  FROM " +
			"    SdRole b" +
			"  WHERE" +
			"    b.sdRoleId = ?1")
	SdRole findBySdRoleId(Long sdRoleId);

	@Query("SELECT rop " +
			"  FROM " +
			"    SdRole r " +
			"    JOIN ProjectSd psd ON (r.serviceDisciplineId = psd.serviceDisciplineId) " +
			"    JOIN RoleOnAProject rop ON (r.roleOnAProjectId = rop.roleOnAProjectId)" +
			"  WHERE" +
			"    psd.projectSdId = ?1")
	List<RoleOnAProject> findRolesByProjectSdId(Long projectSdId);

	@Query("SELECT rop " +
			"  FROM " +
			"    RoleOnAProject rop " +
			"    JOIN SdRole r ON r.roleOnAProjectId = rop.roleOnAProjectId" +
			"    JOIN ProjectSd psd ON r.serviceDisciplineId = psd.serviceDisciplineId " +
			"  WHERE" +
			"    psd.projectSdId = ?1 " +
			"    AND rop.roleOnAProjectId NOT IN "  +
			"		(SELECT r2.roleOnAProjectId " +
			"			FROM SdRole r2, " +
			"    			 ProjectParticipantSdRole ppsdr " +
			"  		 	WHERE r2.sdRoleId = ppsdr.sdRoleId" +
			"				AND ppsdr.projectParticipantId = ?2 " +
			"				AND ppsdr.projectSdId = ?1) "
			)
	List<RoleOnAProject> findRolesAvailableByProjectSdId(Long projectSdId, Long projectParticipantId);

}
