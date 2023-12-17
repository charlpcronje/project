package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VPpSdRoleStage;

@Repository
public interface VPpSdRoleStageRepository extends CrudRepository<VPpSdRoleStage, Long> {


	@Query("SELECT b" +
			"  FROM " +
			"    VPpSdRoleStage b" +
			"  WHERE" +
			"    b.ppSdRoleStageId = ?1")
	VPpSdRoleStage findByPpSdRoleStageId(Long ppSdRoleStageId);


	@Query("SELECT p FROM VPpSdRoleStage p WHERE p.projectParticipantSdRoleId = ?1 order by OrderNumber")
	List<VPpSdRoleStage> findVPpSdRoleStageByPpSdRid(Long projectParticipantSdRoleId);

	@Query("SELECT p FROM VPpSdRoleStage p WHERE p.projectSdId = ?1 order by OrderNumber")
	List<VPpSdRoleStage> findVPpSdRoleStageByProjectSdId(Long projectSdId);

}


