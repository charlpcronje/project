package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

	@Override
	List<Task> findAll();

	@Query("SELECT r FROM Task r WHERE r.taskId = ?1")
	Task findByTaskId(Long taskId);

	@Query("SELECT max(tr.revisionNumber) FROM TaskRevision tr WHERE tr.taskId = ?1")
	Integer getMaxRevisionNumber(Long taskId);
}
