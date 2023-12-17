package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.DeliverableSchedule;

public interface DeliverableScheduleService {
	List<DeliverableSchedule> findAll();

	DeliverableSchedule findByDeliverableScheduleId(Long deliverableScheduleId);

	DeliverableSchedule save(DeliverableSchedule deliverableSchedule);
}
