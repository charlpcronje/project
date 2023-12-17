package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ResourceRemunType;

public interface ResourceRemunTypeService {

	List<ResourceRemunType> findAll();

	ResourceRemunType findByResourceRemunTypeId(Long resourceRemunTypeId);

	ResourceRemunType save(ResourceRemunType resourceRemunType);

}
