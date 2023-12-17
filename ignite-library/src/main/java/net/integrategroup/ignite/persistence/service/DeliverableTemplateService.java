package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.DeliverableTemplate;

public interface DeliverableTemplateService {
	List<DeliverableTemplate> findAll();

	DeliverableTemplate findByDeliverableTemplateId(Long deliverableTemplateId);

	DeliverableTemplate save(DeliverableTemplate deliverableTemplate);
}
