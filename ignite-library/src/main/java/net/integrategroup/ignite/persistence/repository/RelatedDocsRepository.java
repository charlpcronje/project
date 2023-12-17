package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.RelatedDocs;

@Repository
public interface RelatedDocsRepository extends CrudRepository<RelatedDocs, Long> {

	@Query("SELECT a FROM RelatedDocs a")
	List<RelatedDocs> getRelatedDocs();

	@Query("SELECT a FROM RelatedDocs a WHERE relatedDocsID = ?1")
	RelatedDocs findByRelatedDocsId(Long relatedDocsId);

	@Query("SELECT a FROM RelatedDocs a WHERE relatedDocsId not in (Select relatedDocsId from ProcedureRelatedDocs where genProcedureId = ?1)")
	List<RelatedDocs> findRelatedDocsNotLinked(Long genProcedureId);

}
