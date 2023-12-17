package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.DeliverableFormat;


public interface DeliverableFormatService {
	List<DeliverableFormat> findAll();

	DeliverableFormat findByDeliverableFormatCode(String deliverableFormatCode);

	DeliverableFormat save(DeliverableFormat deliverableDependency);
}