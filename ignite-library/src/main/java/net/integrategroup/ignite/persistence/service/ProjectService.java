package net.integrategroup.ignite.persistence.service;

import java.util.List;

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

public interface ProjectService {

	List<Project> findAll();

	List<VProjectTree> findAllInView();

	Project save(Project project);

	List<VProjectTree> getTree();

	VProject findByProjectId(Long projectId);

	VProjectTree findProjectViewByProjectId(Long projectId);

	List<VProjectList> findAllProjectsInView();

//	List<VProjectList> findNonSustenanceProjectsInView();
//
//	List<VProjectList> findOnlySustenanceProjectsInView();

//	void delete(Long projectId, String username) throws SQLException;

	VProjectList findProjectListViewByProjectId(Long projectId);

//	List<ProjectOperationalServiceDisciplineView> findProjectOSDs(Long projectId);

	List<ProjectStage> findProjectStages(Long projectId);

	List<ProjectStage> findProjectStagesRemaining(Long projectId, Long projectSdId);

	List<VProjectSdStage> getProjectSdStagesRemaining(Long projectSdId, Long projectParticipantSdRoleId);


	Long getNextProjectNumberBigInt(Long participantIdHost);

	//String getCurrentProjectStage(Long projectId);

	List<VProjectSd> findProjectSds(Long projectId);

	List<VProjectSd> findProjectSdsNotSelectedForParticipant(Long projectId, Long projectParticipantId);

	List<VServiceDiscipline> getProjectSdsNotSelected(Long projectId);

	List<VPPTree> getPPTree(Long projectId);

	List<VPPTree> getPPTreeChildren(Long ProjectParticipantIdAboveMe);

	List<VProjectParticipantSdRoles> getProjectSdAssignedResources(Long projectSdId);

	List<VProjectSdStage> getProjectSdStages(Long projectSdId);

	List<VProjectSdStage> getProjectSdsForStageId(Long projectStageId);

	List<Project> findProjectByBehalfOfId(Long participantIdOnBehalfOf, Long projectId);

	List<VProjectList> findOnlyMyProjects(Long participantId);
	
	List<VProjectList> findOnlyMyProjectsSustenance(Long participantId);
	
	List<VProjectList> findAllProjects();
	
	List<VProjectList> findAllProjectsSustenance();	
	


	
	
	List<VProjectParticipantSdRoles> getPPSdAndRole(Long projectParticipantId);

}
