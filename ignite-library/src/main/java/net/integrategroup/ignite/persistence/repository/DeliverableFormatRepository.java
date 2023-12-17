package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.DeliverableFormat;

@Repository
public interface DeliverableFormatRepository extends CrudRepository<DeliverableFormat, String>{
	@Override
	List<DeliverableFormat> findAll();

	@Query("SELECT d FROM DeliverableFormat d WHERE d.deliverableFormatCode = ?1")
	DeliverableFormat findByDeliverableFormatCode(String deliverableFormatCode);

}
