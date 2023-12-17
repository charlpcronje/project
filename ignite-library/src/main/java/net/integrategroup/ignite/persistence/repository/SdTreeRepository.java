package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VServiceDiscipline;

@Repository
public interface SdTreeRepository extends CrudRepository<VServiceDiscipline, Long> {

	@Query("SELECT sd FROM VServiceDiscipline sd WHERE sd.level = '1' ORDER BY sd.rowOrderNo")
	List<VServiceDiscipline> findTopLevel();

	@Query("SELECT sd FROM VServiceDiscipline sd WHERE sd.serviceDisciplineIdParent = ?1 ORDER BY sd.rowOrderNo")
	List<VServiceDiscipline> findChildren(Long serviceDisciplineId);

	@Query("SELECT sd FROM VServiceDiscipline sd ORDER BY sd.rowOrderNo")
	List<VServiceDiscipline> getTree();

	@Query("SELECT sd FROM VServiceDiscipline sd WHERE sd.level = '1' ORDER BY sd.rowOrderNo")
	List<VServiceDiscipline> getList();

	}

