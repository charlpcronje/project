package net.integrategroup.ignite.persistence.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.DeliverableType;
import net.integrategroup.ignite.persistence.repository.DeliverableTypeRepository;

@Service
public class DeliverableTypeServiceImpl implements DeliverableTypeService {

	@Autowired
	DeliverableTypeRepository deliverableTypeRepository;

	@Override
	public List<DeliverableType> findAll() {
		return deliverableTypeRepository.findAll();
	}

	@Override
	public DeliverableType save(DeliverableType deliverableType) {
		return deliverableTypeRepository.save(deliverableType);
	}
	
	@Override
	public DeliverableType findByDeliverableTypeId(Long deliverableTypeId) {
		return deliverableTypeRepository.findByDeliverableTypeId(deliverableTypeId);
	}

	@Override
	public List<DeliverableType> findByServiceDisciplineIdIndustry(Long serviceDisciplineIdIndustry) {
		return deliverableTypeRepository.findByServiceDisciplineIdIndustry(serviceDisciplineIdIndustry);
	}
}
