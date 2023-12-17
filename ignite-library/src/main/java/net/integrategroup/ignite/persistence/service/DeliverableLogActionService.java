package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.DeliverableLogAction;

public interface DeliverableLogActionService {
	List<DeliverableLogAction> findAll();

	DeliverableLogAction findByDeliverableLogActionCode(String deliverableLogActionCode);

	DeliverableLogAction save(DeliverableLogAction deliverableLogAction);
}
