package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Deliverable;

public interface DeliverableService {
	List<Deliverable> findAll();

	Deliverable findByDeliverableId(Long deliverableId);

	Deliverable save(Deliverable deliverable);
}

