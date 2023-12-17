package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ResourceRemunType;

@Repository
public interface ResourceRemunTypeRepository extends CrudRepository<ResourceRemunType, Long> {

	@Override
	List<ResourceRemunType> findAll();

	@Query("SELECT r FROM ResourceRemunType r WHERE resourceRemunTypeId = ?1")
	public ResourceRemunType findByResourceRemunTypeId(Long resourceRemunTypeId);

}
