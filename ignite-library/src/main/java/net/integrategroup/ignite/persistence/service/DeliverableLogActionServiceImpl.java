package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.DeliverableLogAction;
import net.integrategroup.ignite.persistence.repository.DeliverableLogActionRepository;

@Service
public class DeliverableLogActionServiceImpl implements DeliverableLogActionService{
	@Autowired
	DeliverableLogActionRepository deliverableLogActionRepository;
	@Override
	public List<DeliverableLogAction> findAll() {
		return deliverableLogActionRepository.findAll();
	}

	@Override
	public DeliverableLogAction findByDeliverableLogActionCode(String deliverableLogActionCode) {
		return deliverableLogActionRepository.findByDeliverableLogActionCode(deliverableLogActionCode);
	}

	@Override
	public DeliverableLogAction save(DeliverableLogAction deliverableLogAction) {
		return deliverableLogActionRepository.save(deliverableLogAction);
	}

}
