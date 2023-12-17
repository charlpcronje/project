package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Country;
import net.integrategroup.ignite.persistence.model.VCountry;
import net.integrategroup.ignite.persistence.repository.CountryRepository;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryRepository countryRepository;

	@Override
	public List<Country> findAll() {
		return countryRepository.findAll();
	}

	@Override
	public Country findByCountryId(Long countryId) {
		return countryRepository.findByCountryId(countryId);
	}

	@Override
	public Country save(Country country) {
		return countryRepository.save(country);
	}

	@Override								//List from VCountry without parameter
	public List<VCountry> findListVCountryOrderByName() {
		return countryRepository.findListVCountryOrderByName();
	}



	//@Override
	//public void delete(Country country) {
	//	countryRepository.delete(country);
	//}

}
