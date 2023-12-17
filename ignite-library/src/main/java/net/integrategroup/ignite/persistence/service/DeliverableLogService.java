package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.DeliverableLog;


public interface DeliverableLogService {
	List<DeliverableLog> findAll();

	DeliverableLog findByDeliverableLogId(Long deliverableLogId);

	DeliverableLog save(DeliverableLog deliverableLog);
}