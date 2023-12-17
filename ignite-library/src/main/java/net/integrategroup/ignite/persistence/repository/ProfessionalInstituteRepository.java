package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProfessionalInstitute;


/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-16 19:51:57 ******** ***** **/

@Repository
public interface ProfessionalInstituteRepository extends CrudRepository<ProfessionalInstitute, Long> {

	@Override
	List<ProfessionalInstitute> findAll();

	@Query("Select i   FROM  ProfessionalInstitute i    WHERE  i.professionalInstituteId = ?1")
	ProfessionalInstitute getByProfessionalInstituteId(Long professionalInstituteId);


//	@Query("Select v   FROM  VProfessionalInstitute v   WHERE  v.professionalInstituteId = ?1")       //One record from View
//	VProfessionalInstitute getByVProfessionalInstituteId(Long professionalInstituteId);



}