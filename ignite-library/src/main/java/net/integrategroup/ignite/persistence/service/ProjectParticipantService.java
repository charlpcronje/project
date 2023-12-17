package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.ProjectParticipant;
import net.integrategroup.ignite.persistence.model.RemunerationRateUpline;
import net.integrategroup.ignite.persistence.model.VBankCard;
import net.integrategroup.ignite.persistence.model.VPPTree;
import net.integrategroup.ignite.persistence.model.VProjectParticipant;
import net.integrategroup.ignite.persistence.model.VProjectParticipantList;
import net.integrategroup.ignite.persistence.model.VProjectParticipantPayerBen;
//import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;
import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;

public interface ProjectParticipantService {

	List<ProjectParticipant> findAll();

	List<VPPTree> findAllForProjectId(Long projectId);

	ProjectParticipant save(ProjectParticipant projectParticipant);

	ProjectParticipant findByProjectParticipantId(Long projectParticipantId);

	//void delete(Long projectParticipantId, String username) throws SQLException;
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	List<ProjectParticipant> findContractedParticipantList(Long projectId);

	List<VPPTree> getParticipantChildrenList(Long projectParticipantId);
//	List<VProjectParticipant> getParticipantChildrenList(Long projectParticipantId);

	List<VProjectParticipantPayerBen> findAllInPPViewForProjectId(Long projectId);

	List<VProjectParticipant> findAllPPForParticipantId(Long participantId);

	RemunerationRateUpline getRemunerationRateUplineForDate(Long participantId, Long projectParticipantIdAboveMe, Date activityDate);

	List<VProjectParticipantSdRoles> findSdAndRolesForProjectParticipantId(Long projectParticipantId);

	List<VProjectParticipantSdRoles> findProjectSdAndRolesForParticipant(Long participantId);

	List<VPPTree> getProjectRelationshipList(Long projectId);

	List<VProjectParticipantPayerBen> findProjectsForParticipantIds(Long participantIdContracting, Long participantIdContracted);

	List<VProjectParticipantList> findOnlyMyProjectsAll(Long participantId);

	List<VProjectParticipant> findByUsername(String username);
}
