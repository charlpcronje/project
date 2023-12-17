package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.DeliverableScheduleTemplate;

public interface DeliverableScheduleTemplateService {
	List<DeliverableScheduleTemplate> findAll();

	DeliverableScheduleTemplate findByDeliverableScheduleTemplateId(Long deliverableScheduleTemplateId);

	DeliverableScheduleTemplate save(DeliverableScheduleTemplate deliverableScheduleTemplate);
}
