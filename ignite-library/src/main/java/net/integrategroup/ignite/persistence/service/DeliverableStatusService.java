package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.DeliverableStatus;

public interface DeliverableStatusService {
	List<DeliverableStatus> findAll();

	DeliverableStatus findByDeliverableStatusCode(String deliverableStatusCode);

	DeliverableStatus save(DeliverableStatus deliverableStatus);
}
