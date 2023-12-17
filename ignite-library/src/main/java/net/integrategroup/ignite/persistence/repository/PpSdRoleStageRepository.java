package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.PpSdRoleStage;

@Repository
public interface PpSdRoleStageRepository extends CrudRepository<PpSdRoleStage, Long> {

	@Override
	List<PpSdRoleStage> findAll();


	@Query("SELECT cl FROM PpSdRoleStage cl WHERE cl.ppSdRoleStageId = ?1")
	PpSdRoleStage findByPpSdRoleStageId(Long ppSdRoleStageId);

	@Query("SELECT p FROM PpSdRoleStage p WHERE p.projectParticipantSdRoleId = ?1 order by OrderNumber")
	List<PpSdRoleStage> findPpSdRoleStageByPpSdRid(Long projectParticipantSdRoleId);
}
