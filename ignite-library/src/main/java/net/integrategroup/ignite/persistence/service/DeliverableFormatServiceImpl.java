package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.DeliverableFormat;
import net.integrategroup.ignite.persistence.repository.DeliverableFormatRepository;

@Service
public class DeliverableFormatServiceImpl implements DeliverableFormatService {
	@Autowired
	DeliverableFormatRepository deliverableFormatRepository;

	@Override
	public List<DeliverableFormat> findAll() {
		return deliverableFormatRepository.findAll();
	}
	
	@Override
	public DeliverableFormat findByDeliverableFormatCode(String deliverableFormatCode) {
		return deliverableFormatRepository.findByDeliverableFormatCode(deliverableFormatCode);
	}

	@Override
	public DeliverableFormat save(DeliverableFormat deliverableFormat) {
		return deliverableFormatRepository.save(deliverableFormat);
	}
}
