package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.GenProcedure;

@Repository
public interface GenProcedureRepository extends CrudRepository<GenProcedure, Long> {

	@Override
	List<GenProcedure> findAll();

	@Query("SELECT p FROM GenProcedure p WHERE p.genProcedureId = ?1")
	GenProcedure findByGenProcedureId(Long procedureId);

	@Query("SELECT p FROM GenProcedure p WHERE p.genProcedureId = ?1")
	List<GenProcedure> findAllGenProceduresForServiceDisciplineId(Long serviceDisciplineId);

	@Query("SELECT max(p.procedureNumber) + 1 FROM GenProcedure p WHERE p.serviceDisciplineId = ?1")
	Integer getNextGenProcedureNumber(Long serviceDisciplineId);
}
