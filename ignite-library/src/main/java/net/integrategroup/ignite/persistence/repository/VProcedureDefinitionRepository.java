package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VProcedureDefinition;

@Repository
public interface VProcedureDefinitionRepository extends CrudRepository<VProcedureDefinition, Long> {


	@Query("SELECT p FROM VProcedureDefinition p order by ProcedureDefinitionId asc")
	List<VProcedureDefinition> findAllProcedureDefinition();

	@Query("SELECT p FROM VProcedureDefinition p WHERE p.definitionId = ?1")
	List<VProcedureDefinition> findByVPDbyDefinitionId(Long definitionId);

	@Query("SELECT p FROM VProcedureDefinition p WHERE p.genProcedureId = ?1")
	List<VProcedureDefinition> findByVPDbyProcedureId(Long genProcedureId);



}


