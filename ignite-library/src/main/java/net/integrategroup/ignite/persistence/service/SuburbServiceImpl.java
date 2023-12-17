package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Suburb;
import net.integrategroup.ignite.persistence.model.VSuburb;
import net.integrategroup.ignite.persistence.model.VSuburbMin;
import net.integrategroup.ignite.persistence.repository.SuburbRepository;

/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-10 10:46:17 ******** ***** **/

@Service
public class SuburbServiceImpl implements SuburbService {

	@Autowired
	SuburbRepository suburbRepository;

	@Override
	public List<Suburb> findAll() {
		return suburbRepository.findAll();
	}

	@Override
	public Suburb getBySuburbId(Long suburbId) {
		return suburbRepository.getBySuburbId(suburbId);
	}

	@Override
	public Suburb save(Suburb suburb) {
		return suburbRepository.save(suburb);
	}

//	@Override                               //One record from View
//	public VSuburb getByVSuburbId(Long suburbId) {
//		return suburbRepository.getByVSuburbId(suburbId);
//	}


	@Override								//All in view
	public List<VSuburb> findListAllInView() {
		return suburbRepository.findListAllInView();
	}


//	@Override								//List from View without parameter
//	public List<VSuburb> findListSuburbXXX() {
//		return suburbRepository.findListVSuburbXXX();
//	}

	@Override								//List from Min View that needs parameter
	public List<VSuburbMin> findListVSuburbMinForCity(Long cityId) {
		return suburbRepository.findListVSuburbMinForCity(cityId);
	}

//	@Override								//List from View that needs multiple parameters
//	public List<VSuburb> findListSuburbXXX(Long paramId, String paramName, String paramName2) {
//		return suburbRepository.findListVSuburbXXX(paramId, paramName, paramName2);
//	}

//	@Override								//List from View that needs date parameters
//	public List<VSuburb> findListSuburbXXX(Long paramId, Date fd, Date ld) {
//		return suburbRepository.findListVSuburbXXX(paramId, fd, ld);
//	}



	@Override
	public List<VSuburb> findListSuburbForCityId(Long cityId) {
		return suburbRepository.findListSuburbForCityId(cityId);
	}



}