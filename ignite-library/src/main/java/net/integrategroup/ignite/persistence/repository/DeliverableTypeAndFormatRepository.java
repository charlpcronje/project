package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.DeliverableTypeAndFormat;

@Repository
public interface DeliverableTypeAndFormatRepository extends CrudRepository<DeliverableTypeAndFormat, Long>{
	@Override
	List<DeliverableTypeAndFormat> findAll();

	@Query("SELECT d FROM DeliverableTypeAndFormat d WHERE d.deliverableTypeAndFormatId = ?1")
	DeliverableTypeAndFormat findByDeliverableTypeAndFormatId(Long deliverableTypeAndFormatId);
}