package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.TaskType;

@Repository
public interface TaskTypeRepository extends CrudRepository<TaskType, Long> {

	@Query("SELECT a FROM TaskType a")
	List<TaskType> getTaskType();

	@Query("SELECT a FROM TaskType a WHERE taskTypeID = ?1")
	TaskType findByTaskTypeId(Long taskTypeId);

	@Query("SELECT tt" +
			"  FROM " +
			"    TaskType tt" +
			"  WHERE" +
			"    tt.assignmentTypeId = ?1")
	List<TaskType> findByAssignmentTypeId(long assignmentTypeId);


}
