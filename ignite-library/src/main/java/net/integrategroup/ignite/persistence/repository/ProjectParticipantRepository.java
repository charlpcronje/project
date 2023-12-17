package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProjectParticipant;
import net.integrategroup.ignite.persistence.model.RemunerationRateUpline;
import net.integrategroup.ignite.persistence.model.VPPTree;
import net.integrategroup.ignite.persistence.model.VProjectParticipant;
import net.integrategroup.ignite.persistence.model.VProjectParticipantList;
import net.integrategroup.ignite.persistence.model.VProjectParticipantPayerBen;
import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;
//import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;

@Repository
public interface ProjectParticipantRepository extends CrudRepository<ProjectParticipant, Long> {

	@Override
	List<ProjectParticipant> findAll();

//	@Query("SELECT pp FROM VProjectParticipant pp WHERE pp.projectId = ?1")
//	List<VProjectParticipant> findAllForProjectId(Long projectId);

	@Query("SELECT pp FROM VPPTree pp WHERE pp.projectId = ?1")
	List<VPPTree> findAllForProjectId(Long projectId);


	@Query("SELECT pp FROM ProjectParticipant pp WHERE pp.projectParticipantId = ?1")
	ProjectParticipant findByProjectParticipantId(Long projectParticipantId);

	@Query("SELECT pp FROM ProjectParticipant pp WHERE pp.projectParticipantIdAboveMe = ?1")
	List<ProjectParticipant> findChildren(Long projectParticipantId);

	@Query("SELECT pp FROM ProjectParticipant pp WHERE pp.projectId = ?1 AND pp.projectParticipantIdAboveMe IS NULL")
	List<ProjectParticipant> findContractedParticipantList(Long projectId);

	@Query("SELECT pp FROM VPPTree pp WHERE pp.projectParticipantIdContracting = ?1")
	List<VPPTree> getParticipantChildrenList(Long projectParticipantId);

//	@Query("SELECT pp FROM VProjectParticipant pp WHERE pp.projectParticipantId = ?1")
//	List<VProjectParticipant> getParticipantChildrenList(Long projectParticipantId);
//
	@Query("SELECT ppv FROM VProjectParticipantPayerBen ppv WHERE ppv.projectId = ?1")
	List<VProjectParticipantPayerBen> findAllInPPViewForProjectId(Long projectId);

	@Query("SELECT ppv FROM VProjectParticipant ppv WHERE ppv.participantIdPayer = ?1 AND ppv.subProjNumber IS NOT NULL")
	List<VProjectParticipant> findAllPPForParticipantId(Long participantId);


	@Query("select cru FROM RemunerationRateUpline cru WHERE cru.remunerationRateUplineId = getRemunerationRateUplineIdForDate(?1, ?2, ?3)")
	RemunerationRateUpline getRemunerationRateUplineForDate(Long participantId, Long projectParticipantIdAboveMe, Date activityDate);

	@Query("SELECT ppsdrv FROM VProjectParticipantSdRoles ppsdrv WHERE ppsdrv.projectParticipantId = ?1")
	List<VProjectParticipantSdRoles> findSdAndRolesForProjectParticipantId(Long projectParticipantId);

	@Query("SELECT ppsdrv FROM VProjectParticipantSdRoles ppsdrv WHERE ppsdrv.participantIdBeneficiary = ?1")
	List<VProjectParticipantSdRoles> findProjectSdAndRolesForParticipant(Long participantId);


	@Query("SELECT pp FROM VPPTree pp WHERE pp.projectId = ?1 and pp.level >1")
	List<VPPTree> getProjectRelationshipList(Long projectId);

	@Query("SELECT ppv FROM VProjectParticipantPayerBen ppv WHERE ppv.participantIdPayer = ?1 AND ppv.participantIdBeneficiary = ?2")
	List<VProjectParticipantPayerBen> findProjectsForParticipantIds(Long participantIdContracting, Long participantIdContracted);
	
	@Query("	SELECT pp"
			+ " FROM VProjectParticipantList pp"
			+ " WHERE"
			+ "     pp.participantId = ?1"			
			+ "		AND pp.subProjNumber != NULL")
//			+ "		AND "
//			+ "		(vpl.participantIdLevel1 = ?1"
//			+ "		OR	vpl.participantIdHost = ?1"
//			+ "		OR 	i.participantId = ?1)")
	List<VProjectParticipantList> findOnlyMyProjectsAll(Long participantId);

	@Query("SELECT " +
		   "    pp " +
		   "  FROM " +
		   "    VProjectParticipant pp,"+
		   "    Individual i" +
		   "  WHERE " +
		   "    pp.individualId = i.individualId" +
		   "    and i.userName = ?1")
	List<VProjectParticipant> findByUsername(String username);		

}
