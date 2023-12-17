package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.DeliverableLogAction;

@Repository
public interface DeliverableLogActionRepository extends CrudRepository<DeliverableLogAction, String>{
	@Override
	List<DeliverableLogAction> findAll();

	@Query("SELECT d FROM DeliverableLogAction d WHERE d.deliverableLogActionCode = ?1")
	DeliverableLogAction findByDeliverableLogActionCode(String deliverableLogActionCode);
}