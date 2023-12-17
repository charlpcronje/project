package net.integrategroup.ignite.persistence.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProjectSdStage;

@Repository
public interface ProjectSdStageRepository extends CrudRepository<ProjectSdStage, Long> {

	@Override
	List<ProjectSdStage> findAll();

	@Query("SELECT p FROM ProjectSdStage p WHERE p.projectSdStageId = ?1")
	ProjectSdStage findByProjectSdStageId(Long projectSdStageId);

	@Query("SELECT psds.projectSdStageId,"
			+ " psds.projectSdId,"
			+ " psds.projectStageId,"
			+ " ps.orderNumber,"
			+ " ps.stageName,"
			+ " ps.description,"
			+ " ps.startDate,"
			+ " ps.endDate"
	+ " FROM ProjectSdStage psds"
	+ " LEFT JOIN ProjectStage ps ON psds.projectStageId = ps.projectStageId"
	+ " WHERE psds.projectSdId = ?1"
	+ " ORDER BY ps.orderNumber ")
	List<ProjectSdStage> findProjectSdStageByProjectSd(Long projectSdId);


// Voorbeelde van queries :

//	@Query("SELECT" + "    COUNT(1) AS usernameCount" + "  FROM" + "    Individual i" + "  WHERE"
//			+ "    TRIM(LOWER(i.userName)) = TRIM(LOWER(?1))")
//	Integer getUsernameUsageCount(String username);
//
//	@Query("SELECT pv FROM VProject pv WHERE pv.projectId = ?1")
//	VProject findProjectViewByProjectId(Long projectId);
//
//	@Query("SELECT plv FROM VProjectList plv")
//	List<VProjectList> findAllProjectsInView();
//
//	@Query("SELECT vpsd FROM VProjectSd vpsd WHERE vpsd.projectId = ?1 ")
//	List<VProjectSd> findProjectSds(Long projectId);
//
//	@Query("SELECT vpsd "
//			+ " FROM VProjectSd vpsd"
//			+ " WHERE vpsd.projectId = ?1 "
//			+ " AND vpsd.sdCode NOT IN"
//			+ " (SELECT psd.serviceDisciplineId "
//			+ "  FROM   ProjectParticipantSdRole ppsdr,"
//			+ "         ProjectSd psd"
//			+ "  WHERE  ppsdr.projectParticipantId = ?2"
//			+ "  AND	psd.projectSdId = ppsdr.projectSdId)")
//	List<VProjectSd> findProjectSdsNotSelectedForParticipant(Long projectId, Long projectParticipantId);


}





