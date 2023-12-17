package net.integrategroup.ignite.persistence.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.DeliverableDependencyTemplate;
import net.integrategroup.ignite.persistence.repository.DeliverableDependencyTemplateRepository;

@Service
public class DeliverableDependencyTemplateServiceImpl implements DeliverableDependencyTemplateService {
	@Autowired
	DeliverableDependencyTemplateRepository deliverableDependencyTemplateRepository;

	@Override
	public List<DeliverableDependencyTemplate> findAll() {
		return deliverableDependencyTemplateRepository.findAll();
	}

	@Override
	public DeliverableDependencyTemplate save(DeliverableDependencyTemplate deliverableDependencyTemplate) {
		return deliverableDependencyTemplateRepository.save(deliverableDependencyTemplate);
	}
	
	@Override
	public DeliverableDependencyTemplate findByDeliverableDependencyTemplateId(Long deliverableDependencyTemplateId) {
		return deliverableDependencyTemplateRepository.findByDeliverableDependencyTemplateId(deliverableDependencyTemplateId);
	}
}
