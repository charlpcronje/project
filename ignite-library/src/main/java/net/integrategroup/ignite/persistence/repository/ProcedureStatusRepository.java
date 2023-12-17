package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProcedureStatus;

@Repository
public interface ProcedureStatusRepository extends CrudRepository<ProcedureStatus, Long> {

	@Override
	List<ProcedureStatus> findAll();

	@Query("SELECT cl FROM ProcedureStatus cl WHERE cl.procedureStatusId = ?1")
	ProcedureStatus findByProcedureStatusId(Long procedureStatusId);

}
