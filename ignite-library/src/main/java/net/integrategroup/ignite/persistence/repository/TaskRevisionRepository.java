package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.TaskRevision;

@Repository
public interface TaskRevisionRepository extends CrudRepository<TaskRevision, Long> {

	@Override
	List<TaskRevision> findAll();

	@Query("SELECT r FROM TaskRevision r WHERE r.taskRevisionId = ?1")
	TaskRevision findByTaskRevisionId(Long taskRevisionId);

	@Query("SELECT t FROM TaskRevision t WHERE t.taskId = ?1")
	List<TaskRevision> findAllTaskRevisionsForTask(Long taskId);

	@Query("SELECT max(tr.revisionNumber) + 1 FROM TaskRevision tr WHERE tr.taskId = ?1")
	Integer getNextRevisionNumber(Long taskId);
}
