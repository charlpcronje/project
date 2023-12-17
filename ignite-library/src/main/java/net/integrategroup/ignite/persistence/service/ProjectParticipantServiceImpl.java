package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ProjectParticipant;
import net.integrategroup.ignite.persistence.model.RemunerationRateUpline;
import net.integrategroup.ignite.persistence.model.VPPTree;
import net.integrategroup.ignite.persistence.model.VProjectParticipant;
import net.integrategroup.ignite.persistence.model.VProjectParticipantList;
import net.integrategroup.ignite.persistence.model.VProjectParticipantPayerBen;
import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;
//import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;
import net.integrategroup.ignite.persistence.repository.ProjectParticipantRepository;

/**
 * @author Ingrid Marais
 *
 */
@Service
public class ProjectParticipantServiceImpl implements ProjectParticipantService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ProjectParticipantRepository projectParticipantRepository;

	@Override
	public List<VPPTree> findAllForProjectId(Long projectId) {
		return projectParticipantRepository.findAllForProjectId(projectId);
	}

	@Override
	public List<ProjectParticipant> findAll() {
		return projectParticipantRepository.findAll();
	}

	@Override
	public ProjectParticipant save(ProjectParticipant projectParticipant) {
		return projectParticipantRepository.save(projectParticipant);
	}

	@Override
	public ProjectParticipant findByProjectParticipantId(Long projectParticipantId) {
		return projectParticipantRepository.findByProjectParticipantId(projectParticipantId);
	}

	/*
	@Override
	public void delete(Long projectParticipantId, String username) throws SQLException {

		String sql = "CALL ig_db.deleteProjectParticipant(?,?);";

		try (CallableStatement cstm = databaseService.prepareCall(sql)) {
			// Todo: Create an audit record to track who deleted the Project
			// cstm.setLong(1, jsonString);
			// cstm.setString(2, securityUtils.getUsername());
			// cstm.registerOutParameter(3, java.sql.Types.BIGINT);
			cstm.setLong(1, projectParticipantId);
			cstm.setString(2, username);
			cstm.execute();
		}
	}
	*/

	@Override
	public List<ProjectParticipant> findContractedParticipantList(Long projectId) {
		return projectParticipantRepository.findContractedParticipantList(projectId);
	}

	@Override
	public List<VPPTree> getParticipantChildrenList(Long projectParticipantId) {
		return projectParticipantRepository.getParticipantChildrenList(projectParticipantId);
	}
//	@Override
//	public List<VProjectParticipant> getParticipantChildrenList(Long projectParticipantId) {
//		return projectParticipantRepository.getParticipantChildrenList(projectParticipantId);
//	}

	@Override
	public List<VProjectParticipantPayerBen> findAllInPPViewForProjectId(Long projectId){
		return projectParticipantRepository.findAllInPPViewForProjectId(projectId);
	}

	@Override
	public List<VProjectParticipant> findAllPPForParticipantId(Long participantId){
		return projectParticipantRepository.findAllPPForParticipantId(participantId);
	}

	@Override
	public RemunerationRateUpline getRemunerationRateUplineForDate(Long participantId, Long projectParticipantIdAboveMe, Date activityDate){
		return projectParticipantRepository.getRemunerationRateUplineForDate(participantId, projectParticipantIdAboveMe, activityDate);
	}

	@Override
	public List<VProjectParticipantSdRoles> findSdAndRolesForProjectParticipantId(Long projectParticipantId){
		return projectParticipantRepository.findSdAndRolesForProjectParticipantId(projectParticipantId);
	}

	@Override
	public List<VProjectParticipantSdRoles> findProjectSdAndRolesForParticipant(Long participantId){
		return projectParticipantRepository.findProjectSdAndRolesForParticipant(participantId);
	}

	@Override
	public List<VPPTree> getProjectRelationshipList(Long projectId) {
		return projectParticipantRepository.getProjectRelationshipList(projectId);
	}

	@Override
	public List<VProjectParticipantPayerBen> findProjectsForParticipantIds(Long participantIdContracting, Long participantIdContracted){
		return projectParticipantRepository.findProjectsForParticipantIds(participantIdContracting,participantIdContracted);
	}

	@Override
	public List<VProjectParticipantList> findOnlyMyProjectsAll(Long participantId) {
		return projectParticipantRepository.findOnlyMyProjectsAll(participantId);
	}

	@Override
	public List<VProjectParticipant> findByUsername(String username) {
		return projectParticipantRepository.findByUsername(username);
	}
}
