package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.DeliverableDependency;


public interface DeliverableDependencyService {
	List<DeliverableDependency> findAll();

	DeliverableDependency findByDeliverableDependencyId(Long deliverableDependencyId);

	DeliverableDependency save(DeliverableDependency deliverableDependency);
}
