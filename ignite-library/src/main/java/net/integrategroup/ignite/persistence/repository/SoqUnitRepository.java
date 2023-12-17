package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.SoqUnit;

@Repository
public interface SoqUnitRepository extends CrudRepository<SoqUnit, Long> {
	
	@Query("SELECT su FROM SoqUnit su")
	List<SoqUnit> findAll();
}
