package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VPersonResponsible;

@Repository
public interface VPersonResponsibleRepository extends CrudRepository<VPersonResponsible, Long> {


	@Query("SELECT p FROM VPersonResponsible p order by PersonResponsibleId asc")
	List<VPersonResponsible> findAllPersonResponsible();

	@Query("SELECT p FROM VPersonResponsible p WHERE p.genProcedureId = ?1")
	List<VPersonResponsible> findPRbyProcedureId(Long genProcedureId);

}


