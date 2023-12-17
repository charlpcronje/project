package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.DeliverableScheduleTemplate;
import net.integrategroup.ignite.persistence.repository.DeliverableScheduleTemplateRepository;

@Service
public class DeliverableScheduleTemplateServiceImpl implements DeliverableScheduleTemplateService{
	@Autowired
	DeliverableScheduleTemplateRepository deliverableScheduleTemplateRepository;
	
	@Override
	public List<DeliverableScheduleTemplate> findAll() {
		return deliverableScheduleTemplateRepository.findAll();
	}

	@Override
	public DeliverableScheduleTemplate findByDeliverableScheduleTemplateId(Long deliverableScheduleTemplateId) {
		return deliverableScheduleTemplateRepository.findByDeliverableScheduleTemplateId(deliverableScheduleTemplateId);
	}

	@Override
	public DeliverableScheduleTemplate save(DeliverableScheduleTemplate deliverableScheduleTemplate) {
		return deliverableScheduleTemplateRepository.save(deliverableScheduleTemplate);
	}

}
