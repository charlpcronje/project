package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.DeliverableStatus;
import net.integrategroup.ignite.persistence.repository.DeliverableStatusRepository;

@Service
public class DeliverableStatusServiceImpl implements DeliverableStatusService{

	@Autowired
	DeliverableStatusRepository deliverableStatusRepository;
	@Override
	public List<DeliverableStatus> findAll() {
		return deliverableStatusRepository.findAll();
	}

	@Override
	public DeliverableStatus findByDeliverableStatusCode(String deliverableStatusCode) {
		return deliverableStatusRepository.findByDeliverableStatusCode(deliverableStatusCode);
	}

	@Override
	public DeliverableStatus save(DeliverableStatus deliverableStatus) {
		return deliverableStatusRepository.save(deliverableStatus);
	}

}
