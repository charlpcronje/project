package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.City;
import net.integrategroup.ignite.persistence.model.VCity;
import net.integrategroup.ignite.persistence.model.VCityMin;


/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-06 16:40:42 ******** ***** **/

@Repository
public interface CityRepository extends CrudRepository<City, Long> {

	@Override
	List<City> findAll();

	@Query("Select i   FROM  City i    WHERE  i.cityId = ?1")
	City getByCityId(Long cityId);

//	@Query("Select v   FROM  VCity v   WHERE  v.cityId = ?1")       //One record from View
//	VCity getByVCityId(Long cityId);

	@Query("SELECT v FROM VCity v")									//All in view
	List<VCity> findListAllInView();

//	@Query("SELECT v FROM VCity v WHERE v.columnName = 'XXX'")		//List from View without parameter
//	List<VCity> findListVCityXXX();

	@Query("SELECT v FROM VCity v WHERE v.provinceId = ?1")				//List from View that needs parameter
	List<VCity> findListVCityForProvince(Long provinceId);
	
	@Query("SELECT v FROM VCityMin v WHERE v.provinceId = ?1")				//List from Min View that needs parameter
	List<VCityMin> findListVCityMinForProvince(Long provinceId);	

//	@Query("SELECT v FROM VCity v WHERE v.columnName = ?1 AND v.columnName = ?2 and v.columnName = ?3")	//List from View that needs multiple parameters
//	List<VCity> findListVCityXXX(Long paramId, String paramName, String paramName2);

//	@Query("SELECT v FROM VCity v "									//List from View that needs date parameters
//		" WHERE    v.paramId = ?1 "
//		" AND      v.paramDate  BETWEEN ?2 AND ?3 "
//		" ORDER BY v.paramDate ")
//	List<VCity> findListVCityXXX(Long paramId, Date fd, Date ld);




	@Query("Select i   FROM  VCity i    WHERE  i.countryId = ?1")
	List<VCity> findListCityForCountryId(Long countryId);

	@Query("Select i   FROM  VCity i    WHERE  i.provinceId = ?1")
	List<VCity> findListCityForProvinceId(Long provinceId);


}