package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ResourceType;

@Repository
public interface ResourceTypeRepository extends CrudRepository<ResourceType, Long> {

	@Override
	List<ResourceType> findAll();

	@Query("SELECT rt FROM ResourceType rt WHERE rt.resourceTypeId = ?1")
	ResourceType findByResourceTypeId(Long resourceTypeId);

}
