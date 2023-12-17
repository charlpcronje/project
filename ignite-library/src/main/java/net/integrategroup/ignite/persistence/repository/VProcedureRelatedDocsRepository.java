package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VProcedureRelatedDocs;

@Repository
public interface VProcedureRelatedDocsRepository extends CrudRepository<VProcedureRelatedDocs, Long> {


	@Query("SELECT p FROM VProcedureRelatedDocs p order by ProcedureRelatedDocsId asc")
	List<VProcedureRelatedDocs> findAllProcedureRelatedDocs();



	@Query("SELECT p FROM VProcedureRelatedDocs p WHERE p.genProcedureId = ?1")
	List<VProcedureRelatedDocs> findPRDbyProcedureId(Long genProcedureId);



}


