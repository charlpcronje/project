package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.IndividualCompetency;


@Repository
public interface IndividualCompetencyRepository extends CrudRepository<IndividualCompetency, Long> {

	@Override
	List<IndividualCompetency> findAll();


	@Query("SELECT " + "    i " + "  FROM " + "    IndividualCompetency i " + "  WHERE " + "    i.individualCompetencyId = ?1")
	IndividualCompetency findByIndividualCompetencyId(Long individualCompetencyId);


	@Query("SELECT i "
			+ " FROM IndividualCompetency i "
			+ " WHERE 	i.individualSdRoleId = ?1 ")
	List<IndividualCompetency> getIndividualCompetencyForIndividualSdRole(Long individualSdRoleId);



}
