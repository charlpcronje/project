package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ResourceType;

public interface ResourceTypeService {

	List<ResourceType> findAll();

	ResourceType save(ResourceType resourceType);

	ResourceType findByResourceTypeId(Long resourceTypeId);

	void delete(ResourceType resourceType);

}
