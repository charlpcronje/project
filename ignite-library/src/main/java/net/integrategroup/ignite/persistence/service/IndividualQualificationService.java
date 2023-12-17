package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.IndividualQualification;
import net.integrategroup.ignite.persistence.model.VIndividualQualification;


/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-16 19:51:56 ******** ***** **/

public interface IndividualQualificationService {

	List<IndividualQualification> findAll();

	IndividualQualification getByIndividualQualificationId(Long individualQualificationId);            //retrieves one record, by PrimaryKey

	IndividualQualification save(IndividualQualification individualQualification);


//	VIndividualQualification getByVIndividualQualificationId(Long individualQualificationId);          //One record from View

//	List<VIndividualQualification> findListAllInView();										//All in view

//	List<VIndividualQualification> findListVIndividualQualificationXXX();										//List from View without parameter

	List<VIndividualQualification> findListVIndividualQualificationForParticipant(Long participantId);						//List from View that needs parameter

//	List<VIndividualQualification> findListVIndividualQualificationXXX(Long paramId, String paramName, String paramName2);	//List from View that needs multiple parameters

//	List<VIndividualQualification> findListVIndividualQualificationXXX(Long paramId, Date fd, Date ld);		//List from View that needs date parameters



	List<IndividualQualification> findListIndividualQualificationForIndividualId(Long individualId);

	List<IndividualQualification> findListIndividualQualificationForInstituteQualificationId(Long instituteQualificationId);



}