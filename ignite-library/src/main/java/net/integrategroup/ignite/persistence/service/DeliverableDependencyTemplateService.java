package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.DeliverableDependencyTemplate;


public interface DeliverableDependencyTemplateService {
	List<DeliverableDependencyTemplate> findAll();

	DeliverableDependencyTemplate findByDeliverableDependencyTemplateId(Long deliverableDependencyTemplateId);

	DeliverableDependencyTemplate save(DeliverableDependencyTemplate deliverableDependencyTemplate);
}
