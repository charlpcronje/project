package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Project;
//import net.integrategroup.ignite.persistence.model.ProjectOperationalServiceDisciplineView;
import net.integrategroup.ignite.persistence.model.ProjectStage;
import net.integrategroup.ignite.persistence.model.VPPTree;
import net.integrategroup.ignite.persistence.model.VProject;
import net.integrategroup.ignite.persistence.model.VProjectTree;
import net.integrategroup.ignite.persistence.model.VProjectList;
import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;
//import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;
import net.integrategroup.ignite.persistence.model.VProjectSd;
import net.integrategroup.ignite.persistence.model.VProjectSdStage;
import net.integrategroup.ignite.persistence.model.VServiceDiscipline;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	@Override
	List<Project> findAll();

	@Query("SELECT p FROM VProjectTree p")
	List<VProjectTree> findAllInView();

	@Query("SELECT p FROM VProject p WHERE p.projectId = ?1")
	VProject findByProjectId(Long projectId);

	@Query("SELECT pv FROM VProjectTree pv WHERE pv.projectIdParent IS NULL")
	List<VProjectTree> findTopLevel();

	@Query("SELECT pv FROM VProjectTree pv WHERE pv.projectIdParent = ?1")
	List<VProjectTree> findChildren(Long projectId);

	// @Query("SELECT pv FROM ProjectView pv ORDER BY pv.projectId, pv.rowOrderNo")
	@Query("SELECT pv FROM VProjectTree pv WHERE pv.projectIdParent IS NULL")
	List<VProjectTree> getTree();

	@Query("SELECT pv FROM VProjectTree pv WHERE pv.projectId = ?1")
	VProjectTree findProjectViewByProjectId(Long projectId);

	@Query("SELECT plv FROM VProjectList plv WHERE plv.projectIdParent != NULL")
	List<VProjectList> findAllProjectsInView();

////	@Query("SELECT plv FROM VProjectList plv WHERE plv.projectId <> plv.projectIdSustenance")
//	@Query("SELECT plv FROM VProjectList plv WHERE plv.isSustenanceProject = NULL AND plv.projectIdParent != NULL")
//	List<VProjectList> findNonSustenanceProjectsInView();
//
//	@Query("SELECT plv FROM VProjectList plv WHERE plv.isSustenanceProject = 'Y' AND plv.projectIdParent != NULL")
//	List<VProjectList> findOnlySustenanceProjectsInView();

	@Query("SELECT plv FROM VProjectList plv WHERE plv.projectId = ?1")
	VProjectList findProjectListViewByProjectId(Long projectId);

	@Query("SELECT ps FROM ProjectStage ps WHERE ps.projectId = ?1 ORDER BY ps.startDate ")
	List<ProjectStage> findProjectStages(Long projectId);

	@Query("SELECT (p.latestProjectNumber) FROM Participant p WHERE p.participantId = ?1")
	Long getNextProjectNumberBigInt(Long participantIdHost);

	@Query("SELECT" + "    COUNT(1) AS usernameCount" + "  FROM" + "    Individual i" + "  WHERE"
			+ "    TRIM(LOWER(i.userName)) = TRIM(LOWER(?1))")
	Integer getUsernameUsageCount(String username);

	@Query("SELECT vpsd FROM VProjectSd vpsd WHERE vpsd.projectId = ?1 ")
	List<VProjectSd> findProjectSds(Long projectId);

	@Query("SELECT vpsd "
			+ " FROM VProjectSd vpsd"
			+ " WHERE vpsd.projectId = ?1 "
			+ " AND vpsd.serviceDisciplineId NOT IN"
			+ " (SELECT psd.serviceDisciplineId "
			+ "  FROM   ProjectParticipantSdRole ppsdr,"
			+ "         ProjectSd psd"
			+ "  WHERE  ppsdr.projectParticipantId = ?2"
			+ "  AND	psd.projectSdId = ppsdr.projectSdId)")
	List<VProjectSd> findProjectSdsNotSelectedForParticipant(Long projectId, Long projectParticipantId);

	@Query("	SELECT vsd "
			+ "	FROM 	VServiceDiscipline vsd"
			+ "	WHERE 	vsd.serviceDisciplineId "
			+ " NOT IN "
			+ "		(SELECT psd.serviceDisciplineId from ProjectSd psd WHERE psd.projectId = ?1)"
			+ " AND vsd.anyChildren = 'N' "
			+ " AND vsd.allowRoles = 'Y' "
			+ " ORDER BY vsd.rowOrderNo")
	List<VServiceDiscipline> getProjectSdsNotSelected(Long projectId);

	@Query("SELECT vppt FROM VPPTree vppt WHERE vppt.level = '1' AND vppt.projectId = ?1")
	List<VPPTree> findTopLevelPPTree(Long projectId);

	@Query("SELECT vppt FROM VPPTree vppt WHERE vppt.projectParticipantIdContracting = ?1")
	List<VPPTree> getPPTreeChildren(Long projectParticipantIdAboveMe);

	@Query("SELECT vppsdr FROM VProjectParticipantSdRoles vppsdr WHERE vppsdr.projectSdId = ?1")
	List<VProjectParticipantSdRoles> getProjectSdAssignedResources(Long projectSdId);

	@Query("SELECT vpsds FROM VProjectSdStage vpsds WHERE vpsds.projectSdId = ?1")
	List<VProjectSdStage> getProjectSdStages(Long projectSdId);



	@Query("SELECT vpsds FROM VProjectSdStage vpsds WHERE vpsds.projectSdId = ?1"
			+ " AND vpsds.projectSdStageId not in (SELECT p.projectSdStageId from PpSdRoleStage p WHERE projectParticipantSdRoleId = ?2)"
			+ "ORDER BY vpsds.orderNumber ")
	List<VProjectSdStage> getProjectSdStagesRemaining(Long projectSdId, Long projectParticipantSdRoleId);



	@Query("SELECT ps FROM ProjectStage ps WHERE ps.projectId = ?1 "
			+ " AND ps.projectStageId not in (SELECT p.projectStageId from ProjectSdStage p WHERE projectSdId = ?2)"
			+ "ORDER BY ps.startDate ")
	List<ProjectStage> findProjectStagesRemaining(Long projectId, Long projectSdId);



	@Query("SELECT vpsds FROM VProjectSdStage vpsds WHERE vpsds.projectStageId = ?1")
	List<VProjectSdStage> getProjectSdsForStageId(Long projectStageId);

	@Query("SELECT DISTINCT pr " +
		       "FROM Project pr " +
		       "LEFT JOIN ProjectParticipant pp ON (pp.projectId = pr.projectId) " +
		       "LEFT JOIN TripLogger tl ON (pp.participantId = tl.participantIdOnBehalfOf) " +
		       "LEFT JOIN Participant p ON (p.participantId = tl.participantIdOnBehalfOf) " +
		       "WHERE (pp.participantId = ?1 OR pr.projectId = COALESCE(?2, -999)) " +
		       "AND pr.projectId IN (SELECT plv.projectId FROM VProjectList plv WHERE plv.flagSustenanceProject IS NULL AND plv.projectIdParent IS NOT NULL)")

		List<Project> findProjectByBehalfOfId(Long participantIdOnBehalfOf, Long projectId);


	@Query("	SELECT DISTINCT vpl "
			+ " FROM VProjectList vpl"
			+ " LEFT JOIN ProjectParticipant pp ON (pp.projectId = vpl.projectId)"
			+ "	LEFT JOIN Individual i 	ON (vpl.individualIdProjectAdmin = i.individualId)"
			+ " WHERE"
			+ "		vpl.subProjNumber != NULL"
			+ "     AND vpl.flagSustenanceProject != 'Y'"
			+ "		AND "
			+ "		(vpl.participantIdLevel1 = ?1"
			+ "		OR	vpl.participantIdHost = ?1"
			+ "		OR	pp.participantId = ?1"
			+ "		OR 	i.participantId = ?1)")
	List<VProjectList> findOnlyMyProjects(Long participantId);

	@Query("	SELECT DISTINCT vpl "
			+ " FROM VProjectList vpl"
			+ " LEFT JOIN ProjectParticipant pp ON (pp.projectId = vpl.projectId)"
			+ "	LEFT JOIN Individual i 	ON (vpl.individualIdProjectAdmin = i.individualId)"
			+ " WHERE"
			+ "		vpl.subProjNumber != NULL"
			+ "     AND vpl.flagSustenanceProject = 'Y'"
			+ "		AND "
			+ "		(vpl.participantIdLevel1 = ?1"
			+ "		OR	vpl.participantIdHost = ?1"
			+ "		OR	pp.participantId = ?1"
			+ "		OR 	i.participantId = ?1)")
	List<VProjectList> findOnlyMyProjectsSustenance(Long participantId);
	
	@Query("	SELECT DISTINCT vpl "
			+ " FROM VProjectList vpl"
			+ " WHERE"
			+ "		 vpl.subProjNumber != NULL"
			+ "	 AND vpl.flagSustenanceProject != 'Y'")
	List<VProjectList> findAllProjects();	
	
	@Query("	SELECT DISTINCT vpl "
			+ " FROM VProjectList vpl"
			+ " WHERE"
			+ "		 vpl.subProjNumber != NULL"
			+ "	 AND vpl.flagSustenanceProject = 'Y'")
	List<VProjectList> findAllProjectsSustenance();	
	

	

	
	
	
	@Query("SELECT vppt FROM VProjectParticipantSdRoles vppt "
			+ " WHERE vppt.projectParticipantId = ?1 ")
	List<VProjectParticipantSdRoles> getPPSdAndRole(Long projectParticipantId);



}


