package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProcedureRelatedDocs;

@Repository
public interface ProcedureRelatedDocsRepository extends CrudRepository<ProcedureRelatedDocs, Long> {

	@Override
	List<ProcedureRelatedDocs> findAll();

	@Query("SELECT b FROM ProcedureRelatedDocs b WHERE b.procedureRelatedDocsId = ?1")
	ProcedureRelatedDocs findByProcedureRelatedDocsId(Long procedureRelatedDocsId);
}
