package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.integrategroup.ignite.persistence.model.DeliverableSchedule;
import net.integrategroup.ignite.persistence.repository.DeliverableScheduleRepository;

public class DeliverableScheduleServiceImpl implements DeliverableScheduleService {

	@Autowired
	DeliverableScheduleRepository deliverableScheduleRepository;

	@Override
	public List<DeliverableSchedule> findAll() {
		return deliverableScheduleRepository.findAll();
	}

	@Override
	public DeliverableSchedule findByDeliverableScheduleId(Long deliverableScheduleId) {
		return deliverableScheduleRepository.findByDeliverableScheduleId(deliverableScheduleId);
	}

	@Override
	public DeliverableSchedule save(DeliverableSchedule deliverableSchedule) {
		return deliverableScheduleRepository.save(deliverableSchedule);
	}

}
