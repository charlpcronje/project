package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Deliverable;
import net.integrategroup.ignite.persistence.repository.DeliverableRepository;

@Service
public class DeliverableServiceImpl implements DeliverableService {

	@Autowired
	DeliverableRepository deliverableRepository;

	@Override
	public List<Deliverable> findAll() {
		return deliverableRepository.findAll();
	}

	@Override
	public Deliverable save(Deliverable deliverable) {
		return deliverableRepository.save(deliverable);
	}
	
	@Override
	public Deliverable findByDeliverableId(Long deliverableId) {
		return deliverableRepository.findByDeliverableId(deliverableId);
	}

}