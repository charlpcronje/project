package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.DeliverableStatus;

@Repository
public interface DeliverableStatusRepository extends CrudRepository<DeliverableStatus, String>{
	@Override
	List<DeliverableStatus> findAll();

	@Query("SELECT d FROM DeliverableStatus d WHERE d.deliverableStatusCode = ?1")
	DeliverableStatus findByDeliverableStatusCode(String deliverableStatusCode);
}