package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.DeliverableSchedule;

@Repository
public interface DeliverableScheduleRepository extends CrudRepository<DeliverableSchedule, Long>{
	@Override
	List<DeliverableSchedule> findAll();

	@Query("SELECT d FROM DeliverableSchedule d WHERE d.deliverableScheduleId = ?1")
	DeliverableSchedule findByDeliverableScheduleId(Long deliverableScheduleId);
}