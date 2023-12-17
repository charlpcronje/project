package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.DeliverableTypeAndFormat;
import net.integrategroup.ignite.persistence.repository.DeliverableTypeAndFormatRepository;

@Service
public class DeliverableTypeAndFormatServiceImpl implements DeliverableTypeAndFormatService{
	@Autowired
	DeliverableTypeAndFormatRepository deliverableTypeAndFormatRepository;
	@Override
	public List<DeliverableTypeAndFormat> findAll() {
		return deliverableTypeAndFormatRepository.findAll();
	}

	@Override
	public DeliverableTypeAndFormat findByDeliverableTypeAndFormatId(Long deliverableTypeAndFormatId) {
		return deliverableTypeAndFormatRepository.findByDeliverableTypeAndFormatId(deliverableTypeAndFormatId);
	}

	@Override
	public DeliverableTypeAndFormat save(DeliverableTypeAndFormat deliverableTypeAndFormat) {
		return deliverableTypeAndFormatRepository.save(deliverableTypeAndFormat);
	}
	
}
