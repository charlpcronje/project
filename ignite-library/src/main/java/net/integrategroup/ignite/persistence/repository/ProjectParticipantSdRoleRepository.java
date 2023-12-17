package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProjectParticipantSdRole;
//import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;

@Repository
public interface ProjectParticipantSdRoleRepository extends CrudRepository<ProjectParticipantSdRole, Long> {

	@Override
	List<ProjectParticipantSdRole> findAll();

	@Query("SELECT ppsd FROM ProjectParticipantSdRole ppsd WHERE ppsd.projectParticipantId = ?1")
	List<ProjectParticipantSdRole> getProjectParticipantSdList(Long projectParticipantId);

	@Query("SELECT ppsd FROM ProjectParticipantSdRole ppsd WHERE ppsd.projectParticipantSdRoleId = ?1")
	ProjectParticipantSdRole findByProjectParticipantSdRoleId(Long projectParticipantSdRoleId);





}
