package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Country;
import net.integrategroup.ignite.persistence.model.VCountry;

public interface CountryService {

	List<Country> findAll();

	Country findByCountryId(Long countryId);

	Country save(Country country);

	List<VCountry> findListVCountryOrderByName();									//List from VCountry without parameter
	


	//void delete(Country country);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
