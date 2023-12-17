package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.HumanResource;

@Repository
public interface HumanResourceRepository extends CrudRepository<HumanResource, Long> {

	@Override
	List<HumanResource> findAll();

	@Query("SELECT hr FROM HumanResource hr WHERE hr.humanResourceId = ?1")
	HumanResource findByHumanResourceId(Long id);

	@Query("SELECT hr FROM HumanResource hr WHERE hr.participantIdPayer = ?1")
	List<HumanResource> findHumanResources(Long participantId);

}
