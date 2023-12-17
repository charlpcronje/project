package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.DeliverableTemplate;

@Repository
public interface DeliverableTemplateRepository extends CrudRepository<DeliverableTemplate, Long>{
	@Override
	List<DeliverableTemplate> findAll();

	@Query("SELECT d FROM DeliverableTemplate d WHERE d.deliverableTemplateId = ?1")
	DeliverableTemplate findByDeliverableTemplateId(Long deliverableTemplateId);
}