package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.DeliverableDependency;

@Repository
public interface DeliverableDependencyRepository extends CrudRepository<DeliverableDependency, Long>{
	@Override
	List<DeliverableDependency> findAll();

	@Query("SELECT d FROM DeliverableDependency d WHERE d.deliverableDependencyId = ?1")
	DeliverableDependency findByDeliverableDependencyId(Long deliverableDependencyId);

}