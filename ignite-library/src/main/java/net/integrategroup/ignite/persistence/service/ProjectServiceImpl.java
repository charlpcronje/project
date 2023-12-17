package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import net.integrategroup.ignite.persistence.repository.ProjectRepository;

/**
 * @author Ingrid Marais
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ProjectRepository projectRepository;

	@Override
	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	@Override
	public List<VProjectTree> findAllInView() {
		return projectRepository.findAllInView();
	}
	@Override
	public List<VProjectTree> getTree() {
		return projectRepository.findTopLevel();
	}

	@Override
	public Project save(Project project) {
		return projectRepository.save(project);
	}

	@Override
	public VProject findByProjectId(Long projectId) {
		return projectRepository.findByProjectId(projectId);
	}

	@Override
	public VProjectTree findProjectViewByProjectId(Long projectId) {
		return projectRepository.findProjectViewByProjectId(projectId);
	}

	@Override
	public List<VProjectList> findAllProjectsInView() {
		return projectRepository.findAllProjectsInView();
	}

//	@Override
//	public List<VProjectList> findNonSustenanceProjectsInView() {
//		return projectRepository.findNonSustenanceProjectsInView();
//	}
//
//	@Override
//	public List<VProjectList> findOnlySustenanceProjectsInView() {
//		return projectRepository.findOnlySustenanceProjectsInView();
//	}

//	@Override
//	public void delete(Long projectId, String username) throws SQLException {
//		String sql = "CALL ig_db.deleteProject(?,?);";
//
//		try (CallableStatement cstm = databaseService.prepareCall(sql)) {
//			// Todo: Create an audit record to track who deleted the Project
//			// cstm.setLong(1, jsonString);
//			// cstm.setString(2, securityUtils.getUsername());
//			// cstm.registerOutParameter(3, java.sql.Types.BIGINT);
//			cstm.setLong(1, projectId);
//			cstm.setString(2, username);
//			cstm.execute();
//		}
//	}

	@Override
	public VProjectList findProjectListViewByProjectId(Long projectId) {
		return projectRepository.findProjectListViewByProjectId(projectId);
	}

//	@Override
//	public List<ProjectOperationalServiceDisciplineView> findProjectOSDs(Long projectId) {
//		return projectRepository.findProjectOSDs(projectId);
//	}

	@Override
	public List<ProjectStage> findProjectStagesRemaining(Long projectId, Long projectSdId) {
		return projectRepository.findProjectStagesRemaining(projectId, projectSdId);
	}

	@Override
	public List<VProjectSdStage> getProjectSdStagesRemaining(Long projectSdId, Long projectParticipantSdRoleId) {
		return projectRepository.getProjectSdStagesRemaining(projectSdId, projectParticipantSdRoleId);
	}




	@Override
	public List<ProjectStage> findProjectStages(Long projectId) {
		return projectRepository.findProjectStages(projectId);
	}

	@Override
	public Long getNextProjectNumberBigInt(Long participantIdHost) {
		return projectRepository.getNextProjectNumberBigInt(participantIdHost);
	}


//	@Override
//	public String getCurrentProjectStage(Long projectId) {
//		return projectRepository.getCurrentProjectStage(projectId);
//	}
//
	@Override
	public List<VProjectSd> findProjectSds(Long projectId){
		return projectRepository.findProjectSds(projectId);
	}

	@Override
	public List<VProjectSd> findProjectSdsNotSelectedForParticipant(Long projectId, Long projectParticipantId){
		return projectRepository.findProjectSdsNotSelectedForParticipant(projectId, projectParticipantId);
	}

	@Override
	public List<VServiceDiscipline> getProjectSdsNotSelected(Long projectId){
		return projectRepository.getProjectSdsNotSelected(projectId);
	}

	@Override
	public List<VPPTree> getPPTree(Long projectId) {
		return projectRepository.findTopLevelPPTree(projectId);
	}

	@Override
	public List<VPPTree> getPPTreeChildren(Long projectParticipantIdAboveMe) {
		return projectRepository.getPPTreeChildren(projectParticipantIdAboveMe);
	}

	@Override
	public List<VProjectParticipantSdRoles> getProjectSdAssignedResources(Long projectSdId) {
		return projectRepository.getProjectSdAssignedResources(projectSdId);
	}

	@Override
	public List<VProjectSdStage> getProjectSdStages(Long projectSdId) {
		return projectRepository.getProjectSdStages(projectSdId);
	}

	@Override
	public List<VProjectSdStage> getProjectSdsForStageId(Long projectStageId) {
		return projectRepository.getProjectSdsForStageId(projectStageId);
	}

	@Override
	public List<Project> findProjectByBehalfOfId(Long participantIdOnBehalfOf, Long projectId) {
		return projectRepository.findProjectByBehalfOfId(participantIdOnBehalfOf, projectId);
	}

	@Override
	public List<VProjectList> findOnlyMyProjects(Long participantId) {
		return projectRepository.findOnlyMyProjects(participantId);
	}
	
	@Override
	public List<VProjectList> findOnlyMyProjectsSustenance(Long participantId) {
		return projectRepository.findOnlyMyProjectsSustenance(participantId);
	}
	
	@Override
	public List<VProjectList> findAllProjectsSustenance() {
		return projectRepository.findAllProjectsSustenance();
	}	

	@Override
	public List<VProjectList> findAllProjects() {
		return projectRepository.findAllProjects();
	}	

	

	
	
	@Override
	public List<VProjectParticipantSdRoles> getPPSdAndRole(Long projectParticipantId) {
		return projectRepository.getPPSdAndRole(projectParticipantId);
	}

}

