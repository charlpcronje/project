package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Province;
import net.integrategroup.ignite.persistence.model.VProvince;


/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-06 16:40:44 ******** ***** **/

public interface ProvinceService {

	List<Province> findAll();

	Province getByProvinceId(Long provinceId);            //retrieves one record, by PrimaryKey

	Province save(Province province);


//	VProvince getByVProvinceCode(Long provinceId);          //One record from View

	List<VProvince> findListAllInView();										//All in view

//	List<VProvince> findListVProvinceXXX();										//List from View without parameter

	List<VProvince> findListVProvinceForCountry(Long countryId);						//List from View that needs parameter

//	List<VProvince> findListVProvinceXXX(Long paramId, String paramName, String paramName2);	//List from View that needs multiple parameters

//	List<VProvince> findListVProvinceXXX(Long paramId, Date fd, Date ld);		//List from View that needs date parameters



	List<VProvince> findListProvinceForCountryId(Long countryId);



}