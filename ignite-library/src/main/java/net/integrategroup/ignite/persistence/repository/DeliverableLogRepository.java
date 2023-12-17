package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.DeliverableLog;

@Repository
public interface DeliverableLogRepository extends CrudRepository<DeliverableLog, Long>{
	@Override
	List<DeliverableLog> findAll();

	@Query("SELECT d FROM DeliverableLog d WHERE d.deliverableLogId = ?1")
	DeliverableLog findByDeliverableLogId(Long deliverableLogId);

}
