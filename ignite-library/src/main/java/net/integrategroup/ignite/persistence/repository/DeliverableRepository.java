package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Deliverable;

@Repository
public interface DeliverableRepository extends CrudRepository<Deliverable, Long>{
	@Override
	List<Deliverable> findAll();

	@Query("SELECT d FROM Deliverable d WHERE d.deliverableId = ?1")
	Deliverable findByDeliverableId(Long deliverableId);

}