package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ResourceRemuneration;

@Repository
public interface ResourceRemunerationRepository extends CrudRepository<ResourceRemuneration, Long> {



	@Query("SELECT rem FROM ResourceRemuneration rem WHERE rem.resourceRemunerationId = ?1")
	ResourceRemuneration findByResourceRemunerationId(Long id);

	@Query("SELECT rem FROM ResourceRemuneration rem WHERE rem.nonProjectRelatedAgreementId = ?1")
	List<ResourceRemuneration> findAllForHumanResource(Long id);



}
