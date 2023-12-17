package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Definition;

@Repository
public interface DefinitionRepository extends CrudRepository<Definition, Long> {

	@Query("SELECT a FROM Definition a")
	List<Definition> getDefinition();

	@Query("SELECT a FROM Definition a WHERE definitionID = ?1")
	Definition findByDefinitionId(Long definitionId);

	@Query("SELECT a FROM Definition a WHERE definitionId not in (Select definitionId from ProcedureDefinition where genProcedureId = ?1)")
	List<Definition> findDefinitionNotLinked(Long genProcedureId);

}
