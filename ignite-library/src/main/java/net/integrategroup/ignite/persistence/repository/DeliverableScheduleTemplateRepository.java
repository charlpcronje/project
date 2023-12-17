package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.DeliverableScheduleTemplate;

@Repository
public interface DeliverableScheduleTemplateRepository extends CrudRepository<DeliverableScheduleTemplate, Long>{
	@Override
	List<DeliverableScheduleTemplate> findAll();

	@Query("SELECT d FROM DeliverableScheduleTemplate d WHERE d.deliverableScheduleTemplateId = ?1")
	DeliverableScheduleTemplate findByDeliverableScheduleTemplateId(Long deliverableScheduleTemplateId);
}