package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.DeliverableType;

public interface DeliverableTypeService {
	List<DeliverableType> findAll();

	DeliverableType findByDeliverableTypeId(Long deliverableTypeId);
	List<DeliverableType> findByServiceDisciplineIdIndustry(Long serviceDisciplineIdIndustry);

	DeliverableType save(DeliverableType deliverableType);
}
