package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.RemunerationRateUpline;
import net.integrategroup.ignite.persistence.model.VAgreementParticipants;
import net.integrategroup.ignite.persistence.model.VPPIndividualRatesUpline;
import net.integrategroup.ignite.persistence.model.VRemunerationRateUpline;

public interface RemunerationRateUplineService {

//	List<Project> findAll();

	//public RemunerationRateUpline save(RemunerationRateUpline remunerationRateUpline);

//	public RemunerationRateUpline save(
//			Long remunerationRateUplineId,
//			Long agreementBetweenParticipantsId,
//			Long projectParticipantId,
//			Long participantIdIndividual,
//			Long projBasedRemunTypeId,
//			Long ExpenseTypeId,
//			Double ExpenseHandlingPerc,
//			Double RatePerUnit,
//			String Description,
//			Date startDate,
//			Date endDate) throws SQLException;

//
//	List<V_Project> getTree();
//
//	Project findByProjectId(Long projectId);
//
//	V_Project findProjectViewByProjectId(Long projectId);

	List<VAgreementParticipants> getPPIndividualListWithTimeCostAgreements(Long projectId);

	List<VAgreementParticipants> getAgreementIndividualList(Long agreeementBetweenParticipantsId);

	List<VAgreementParticipants> getAgreementParticipantList(Long agreeementBetweenParticipantsId);

	List<VRemunerationRateUpline> getRatesUpline(Long agreementBetweenParticipantsId, Long projectParticipantId);

	List<VRemunerationRateUpline> getRatesUplineCurrent(Long agreementBetweenParticipantsId, Long projectParticipantId);

	RemunerationRateUpline findByRemunerationRateUplineId(Long remunerationRateUplineId);

//	Integer getUniqueDateRange(	Long remunerationRateUplineId,
//								Long agreementBetweenParticipantsId,
//								Long projectParticipantId,
//								Long participantIdIndividual,
//								Long projBasedRemunTypeId,
//								Date startDate,
//								Date endDate);


	List<VPPIndividualRatesUpline> getRatesUplineForTimesheetEntry(Long timesheetId);


	//
//	List<V_ProjectList> findAllProjectsInView();
//
//	void delete(Long projectId, String username) throws SQLException;
//
//	V_ProjectList findProjectListViewByProjectId(Long projectId);
//
////	List<ProjectOperationalServiceDisciplineView> findProjectOSDs(Long projectId);
//
//	List<ProjectStage> findProjectStages(Long projectId);
//
//	String getNextProjectNumber();
//
//	//String getCurrentProjectStage(Long projectId);
//
//	List<V_ProjectSd> findProjectSds(Long projectId);
//
//	List<V_ProjectSd> findProjectSdsNotSelectedForParticipant(Long projectId, Long projectParticipantId);
//
//	List<V_ServiceDiscipline> getProjectSdsNotSelected(Long projectId);
//
//	List<V_PPTree> getPPTree(Long projectId);
//
//	List<V_PPTree> getPPTreeChildren(Long ProjectParticipantIdAboveMe);
//


}
