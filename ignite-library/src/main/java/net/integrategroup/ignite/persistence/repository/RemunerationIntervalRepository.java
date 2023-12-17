package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import net.integrategroup.ignite.persistence.model.RemunerationInterval;

public interface RemunerationIntervalRepository extends CrudRepository<RemunerationInterval, String> {

	@Override
	List<RemunerationInterval> findAll();

	@Query("SELECT ri FROM RemunerationInterval ri WHERE remunerationIntervalCode = ?1")
	public RemunerationInterval findByRemunerationIntervalCode(String remunerationIntervalCode);
}
