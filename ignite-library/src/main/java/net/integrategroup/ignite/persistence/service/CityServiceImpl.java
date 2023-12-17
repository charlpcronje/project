package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.City;
import net.integrategroup.ignite.persistence.model.VCity;
import net.integrategroup.ignite.persistence.model.VCityMin;
import net.integrategroup.ignite.persistence.repository.CityRepository;

/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-06 16:40:42 ******** ***** **/

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	CityRepository cityRepository;

	@Override
	public List<City> findAll() {
		return cityRepository.findAll();
	}

	@Override
	public City getByCityId(Long cityId) {
		return cityRepository.getByCityId(cityId);
	}

	@Override
	public City save(City city) {
		return cityRepository.save(city);
	}

//	@Override                               //One record from View
//	public VCity getByVCityId(Long cityId) {
//		return cityRepository.getByVCityId(cityId);
//	}


	@Override								//All in view
	public List<VCity> findListAllInView() {
		return cityRepository.findListAllInView();
	}

//	@Override								//List from View without parameter
//	public List<VCity> findListCityXXX() {
//		return cityRepository.findListVCityXXX();
//	}

	@Override								//List from  View that needs parameter
	public List<VCity> findListVCityForProvince(Long provinceId) {
		return cityRepository.findListVCityForProvince(provinceId);
	}


	@Override								//List from Min View that needs parameter
	public List<VCityMin> findListVCityMinForProvince(Long provinceId) {
		return cityRepository.findListVCityMinForProvince(provinceId);
	}

//	@Override								//List from View that needs multiple parameters
//	public List<VCity> findListCityXXX(Long paramId, String paramName, String paramName2) {
//		return cityRepository.findListVCityXXX(paramId, paramName, paramName2);
//	}

//	@Override								//List from View that needs date parameters
//	public List<VCity> findListCityXXX(Long paramId, Date fd, Date ld) {
//		return cityRepository.findListVCityXXX(paramId, fd, ld);
//	}



	@Override
	public List<VCity> findListCityForCountryId(Long countryId) {
		return cityRepository.findListCityForCountryId(countryId);
	}

	@Override
	public List<VCity> findListCityForProvinceId(Long provinceId) {
		return cityRepository.findListCityForProvinceId(provinceId);
	}



}