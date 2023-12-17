package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Province;
import net.integrategroup.ignite.persistence.model.VProvince;


/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-06 16:40:44 ******** ***** **/

@Repository
public interface ProvinceRepository extends CrudRepository<Province, Long> {

	@Override
	List<Province> findAll();

	@Query("Select i   FROM  Province i    WHERE  i.provinceId = ?1")
	Province getByProvinceId(Long provinceId);

	@Query("SELECT v FROM VProvince v")									//All in view
	List<VProvince> findListAllInView();

//	@Query("SELECT v FROM VProvince v WHERE v.columnName = 'XXX'")		//List from View without parameter
//	List<VProvince> findListVProvinceXXX();

	@Query("SELECT v FROM VProvince v WHERE v.countryId = ?1")				//List from View that needs parameter
	List<VProvince> findListVProvinceForCountry(Long countryId);

//	@Query("SELECT v FROM VProvince v WHERE v.columnName = ?1 AND v.columnName = ?2 and v.columnName = ?3")	//List from View that needs multiple parameters
//	List<VProvince> findListVProvinceXXX(Long paramId, String paramName, String paramName2);

//	@Query("SELECT v FROM VProvince v "									//List from View that needs date parameters
//		" WHERE    v.paramId = ?1 "
//		" AND      v.paramDate  BETWEEN ?2 AND ?3 "
//		" ORDER BY v.paramDate ")
//	List<VProvince> findListVProvinceXXX(Long paramId, Date fd, Date ld);




	@Query("Select i   FROM  VProvince i    WHERE  i.countryId = ?1")
	List<VProvince> findListProvinceForCountryId(Long countryId);


}