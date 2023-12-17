package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.DeliverableTemplate;
import net.integrategroup.ignite.persistence.repository.DeliverableTemplateRepository;

@Service
public class DeliverableTemplateServiceImpl implements DeliverableTemplateService{
	@Autowired
	DeliverableTemplateRepository deliverableTemplateRepository;
	@Override
	public List<DeliverableTemplate> findAll() {
		return deliverableTemplateRepository.findAll();
	}

	@Override
	public DeliverableTemplate findByDeliverableTemplateId(Long deliverableTemplateId) {
		return deliverableTemplateRepository.findByDeliverableTemplateId(deliverableTemplateId);
	}

	@Override
	public DeliverableTemplate save(DeliverableTemplate deliverableTemplate) {
		return deliverableTemplateRepository.save(deliverableTemplate);
	}

}
