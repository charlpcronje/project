package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.DeliverableTypeAndFormat;

public interface DeliverableTypeAndFormatService {
	List<DeliverableTypeAndFormat> findAll();

	DeliverableTypeAndFormat findByDeliverableTypeAndFormatId(Long deliverableTypeAndFormatId);

	DeliverableTypeAndFormat save(DeliverableTypeAndFormat deliverableTypeAndFormat);
}
