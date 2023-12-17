package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Country;
import net.integrategroup.ignite.persistence.model.VCountry;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

	@Override
	List<Country> findAll();

	@Query("SELECT c FROM Country c WHERE countryId = ?1")
	Country findByCountryId(Long countryId);

	@Query("SELECT v FROM VCountry v order by v.name")		//List from VCountry without parameter
	List<VCountry> findListVCountryOrderByName();

}
