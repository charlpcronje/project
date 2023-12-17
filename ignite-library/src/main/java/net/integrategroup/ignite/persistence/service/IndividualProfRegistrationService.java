package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.IndividualProfRegistration;
import net.integrategroup.ignite.persistence.model.VIndividualProfRegistration;


/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-16 19:51:56 ******** ***** **/

public interface IndividualProfRegistrationService {

	List<IndividualProfRegistration> findAll();

	IndividualProfRegistration getByIndividualProfRegistrationId(Long individualProfRegistrationId);            //retrieves one record, by PrimaryKey

	IndividualProfRegistration save(IndividualProfRegistration individualProfRegistration);


//	VIndividualProfRegistration getByVIndividualProfRegistrationId(Long individualProfRegistrationId);          //One record from View

//	List<VIndividualProfRegistration> findListAllInView();										//All in view

//	List<VIndividualProfRegistration> findListVIndividualProfRegistrationXXX();										//List from View without parameter

	List<VIndividualProfRegistration> findListVIndividualProfRegistrationForParticipant(Long participantId);						//List from View that needs parameter

//	List<VIndividualProfRegistration> findListVIndividualProfRegistrationXXX(Long paramId, String paramName, String paramName2);	//List from View that needs multiple parameters

//	List<VIndividualProfRegistration> findListVIndividualProfRegistrationXXX(Long paramId, Date fd, Date ld);		//List from View that needs date parameters



	List<IndividualProfRegistration> findListIndividualProfRegistrationForIndividualId(Long individualId);

	List<IndividualProfRegistration> findListIndividualProfRegistrationForProfessionalInstituteId(Long professionalInstituteId);



}