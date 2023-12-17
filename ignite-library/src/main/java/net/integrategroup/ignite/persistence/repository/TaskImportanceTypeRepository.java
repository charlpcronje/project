package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.TaskImportanceType;

@Repository
public interface TaskImportanceTypeRepository extends CrudRepository<TaskImportanceType, Long> {

	@Query("SELECT a FROM TaskImportanceType a")
	List<TaskImportanceType> getTaskImportanceType();

	@Query("SELECT a FROM TaskImportanceType a WHERE TaskImportanceTypeId = ?1")
	TaskImportanceType findByTaskImportanceTypeId(Long taskImportanceTypeId);

}
