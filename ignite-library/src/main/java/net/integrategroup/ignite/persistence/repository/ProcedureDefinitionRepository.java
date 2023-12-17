package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProcedureDefinition;

@Repository
public interface ProcedureDefinitionRepository extends CrudRepository<ProcedureDefinition, Long> {

	@Override
	List<ProcedureDefinition> findAll();

	@Query("SELECT b FROM ProcedureDefinition b WHERE b.procedureDefinitionId = ?1")
	ProcedureDefinition findByProcedureDefinitionId(Long procedureDefinitionId);
}
