package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.DeliverableLog;
import net.integrategroup.ignite.persistence.repository.DeliverableLogRepository;

@Service
public class DeliverableLogServiceImpl implements DeliverableLogService{

	@Autowired
	DeliverableLogRepository deliverableLogRepository;

	@Override
	public List<DeliverableLog> findAll() {
		return deliverableLogRepository.findAll();
	}

	@Override
	public DeliverableLog findByDeliverableLogId(Long deliverableLogId) {
		return deliverableLogRepository.findByDeliverableLogId(deliverableLogId);
	}

	@Override
	public DeliverableLog save(DeliverableLog deliverableLog) {
		return deliverableLogRepository.save(deliverableLog);
	}

}
