package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.DeliverableDependencyTemplate;

@Repository
public interface DeliverableDependencyTemplateRepository extends CrudRepository<DeliverableDependencyTemplate, Long>{
	@Override
	List<DeliverableDependencyTemplate> findAll();

	@Query("SELECT d FROM DeliverableDependencyTemplate d WHERE d.deliverableDependencyTemplateId = ?1")
	DeliverableDependencyTemplate findByDeliverableDependencyTemplateId(Long deliverableDependencyTemplateId);
}