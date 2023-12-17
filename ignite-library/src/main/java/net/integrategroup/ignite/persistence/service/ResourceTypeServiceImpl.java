package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ResourceType;
import net.integrategroup.ignite.persistence.repository.ResourceTypeRepository;

@Service
public class ResourceTypeServiceImpl implements ResourceTypeService {

	@Autowired
	ResourceTypeRepository resourceTypeRepository;

	@Override
	public List<ResourceType> findAll() {
		return resourceTypeRepository.findAll();
	}

	@Override
	public ResourceType save(ResourceType resourceType) {
		return resourceTypeRepository.save(resourceType);
	}

	@Override
	public ResourceType findByResourceTypeId(Long resourceTypeId) {
		return resourceTypeRepository.findByResourceTypeId(resourceTypeId);
	}

	@Override
	public void delete(ResourceType resourceType) {
		resourceTypeRepository.delete(resourceType);
	}

//	@Override
//	public Branch save(Branch branch) {
//		return branchRepository.save(branch);

}
