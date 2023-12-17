package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.DeliverableType;

@Repository
public interface DeliverableTypeRepository extends CrudRepository<DeliverableType, Long>{
	@Override
	List<DeliverableType> findAll();

	@Query("SELECT d FROM DeliverableType d WHERE d.deliverableTypeId = ?1")
	DeliverableType findByDeliverableTypeId(Long deliverableTypeId);
	
	@Query("SELECT d FROM DeliverableType d WHERE d.serviceDisciplineIdIndustry = ?1")
	List<DeliverableType>  findByServiceDisciplineIdIndustry(Long serviceDisciplineIdIndustry);
}
