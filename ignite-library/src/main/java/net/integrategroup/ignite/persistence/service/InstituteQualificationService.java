package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.InstituteQualification;
import net.integrategroup.ignite.persistence.model.VInstituteQualification;


/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-16 19:51:56 ******** ***** **/

public interface InstituteQualificationService {

	List<InstituteQualification> findAll();

	InstituteQualification getByInstituteQualificationId(Long instituteQualificationId);            //retrieves one record, by PrimaryKey

	InstituteQualification save(InstituteQualification instituteQualification);


//	VInstituteQualification getByVInstituteQualificationId(Long instituteQualificationId);          //One record from View

	List<VInstituteQualification> findListAllInView();										//All in view

//	List<VInstituteQualification> findListVInstituteQualificationXXX();										//List from View without parameter

	List<VInstituteQualification> findListVInstituteQualificationForStudyInstitute(Long studyInstituteId);						//List from View that needs parameter

//	List<VInstituteQualification> findListVInstituteQualificationXXX(Long paramId, String paramName, String paramName2);	//List from View that needs multiple parameters

//	List<VInstituteQualification> findListVInstituteQualificationXXX(Long paramId, Date fd, Date ld);		//List from View that needs date parameters



	List<InstituteQualification> findListInstituteQualificationForStudyInstituteId(Long studyInstituteId);



}