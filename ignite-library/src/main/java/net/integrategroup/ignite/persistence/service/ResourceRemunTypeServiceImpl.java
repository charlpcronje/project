package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ResourceRemunType;
import net.integrategroup.ignite.persistence.repository.ResourceRemunTypeRepository;

@Service
public class ResourceRemunTypeServiceImpl implements ResourceRemunTypeService {

	@Autowired
	ResourceRemunTypeRepository resourceRemunTypeRepository;

	@Override
	public List<ResourceRemunType> findAll() {
		return resourceRemunTypeRepository.findAll();
	}

	@Override
	public ResourceRemunType findByResourceRemunTypeId(Long resourceRemunTypeId) {
		return resourceRemunTypeRepository.findByResourceRemunTypeId(resourceRemunTypeId);
	}

	@Override
	public ResourceRemunType save(ResourceRemunType resourceRemunType) {
		return resourceRemunTypeRepository.save(resourceRemunType);
	}



}
