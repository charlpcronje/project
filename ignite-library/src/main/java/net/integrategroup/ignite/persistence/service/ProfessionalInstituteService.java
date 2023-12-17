package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ProfessionalInstitute;


/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-16 19:51:57 ******** ***** **/

public interface ProfessionalInstituteService {

	List<ProfessionalInstitute> findAll();

	ProfessionalInstitute getByProfessionalInstituteId(Long professionalInstituteId);            //retrieves one record, by PrimaryKey

	ProfessionalInstitute save(ProfessionalInstitute professionalInstitute);


//	VProfessionalInstitute getByVProfessionalInstituteId(Long professionalInstituteId);          //One record from View



}