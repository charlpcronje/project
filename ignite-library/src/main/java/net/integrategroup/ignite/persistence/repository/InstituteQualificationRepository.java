package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.InstituteQualification;
import net.integrategroup.ignite.persistence.model.VInstituteQualification;


/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-16 19:51:56 ******** ***** **/

@Repository
public interface InstituteQualificationRepository extends CrudRepository<InstituteQualification, Long> {

	@Override
	List<InstituteQualification> findAll();

	@Query("Select i   FROM  InstituteQualification i    WHERE  i.instituteQualificationId = ?1")
	InstituteQualification getByInstituteQualificationId(Long instituteQualificationId);


//	@Query("Select v   FROM  VInstituteQualification v   WHERE  v.instituteQualificationId = ?1")       //One record from View
//	VInstituteQualification getByVInstituteQualificationId(Long instituteQualificationId);





	@Query("SELECT v FROM VInstituteQualification v")									//All in view
	List<VInstituteQualification> findListAllInView();

//	@Query("SELECT v FROM VInstituteQualification v WHERE v.columnName = 'XXX'")		//List from View without parameter
//	List<VInstituteQualification> findListVInstituteQualificationXXX();

	@Query("SELECT v FROM VInstituteQualification v WHERE v.studyInstituteId = ?1")				//List from View that needs parameter
	List<VInstituteQualification> findListVInstituteQualificationForStudyInstitute(Long studyInstituteId);

//	@Query("SELECT v FROM VInstituteQualification v WHERE v.columnName = ?1 AND v.columnName = ?2 and v.columnName = ?3")	//List from View that needs multiple parameters
//	List<VInstituteQualification> findListVInstituteQualificationXXX(Long paramId, String paramName, String paramName2);

//	@Query("SELECT v FROM VInstituteQualification v "									//List from View that needs date parameters
//		" WHERE    v.paramId = ?1 "
//		" AND      v.paramDate  BETWEEN ?2 AND ?3 "
//		" ORDER BY v.paramDate ")
//	List<VInstituteQualification> findListVInstituteQualificationXXX(Long paramId, Date fd, Date ld);




	@Query("Select i   FROM  InstituteQualification i    WHERE  i.studyInstituteId = ?1")
	List<InstituteQualification> findListInstituteQualificationForStudyInstituteId(Long studyInstituteId);


}