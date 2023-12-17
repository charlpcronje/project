package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Health;

@Repository
public interface HealthRepository extends CrudRepository<Health, String> {

	@Override
	List<Health> findAll();

	@Query("SELECT h FROM Health h WHERE componentName = ?1")
	Health findByComponentName(String componentName);
}
