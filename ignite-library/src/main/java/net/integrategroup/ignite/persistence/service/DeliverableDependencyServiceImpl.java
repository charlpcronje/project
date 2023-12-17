package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.DeliverableDependency;
import net.integrategroup.ignite.persistence.repository.DeliverableDependencyRepository;

@Service
public class DeliverableDependencyServiceImpl implements DeliverableDependencyService {
	@Autowired
	DeliverableDependencyRepository deliverableDependencyRepository;

	@Override
	public List<DeliverableDependency> findAll() {
		return deliverableDependencyRepository.findAll();
	}

	@Override
	public DeliverableDependency save(DeliverableDependency deliverableDependency) {
		return deliverableDependencyRepository.save(deliverableDependency);
	}
	
	@Override
	public DeliverableDependency findByDeliverableDependencyId(Long deliverableDependencyId) {
		return deliverableDependencyRepository.findByDeliverableDependencyId(deliverableDependencyId);
	}
}
