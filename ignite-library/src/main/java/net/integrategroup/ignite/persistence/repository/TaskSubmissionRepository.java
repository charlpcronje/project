package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.TaskSubmission;

@Repository
public interface TaskSubmissionRepository extends CrudRepository<TaskSubmission, Long> {

	@Override
	List<TaskSubmission> findAll();

	@Query("SELECT r FROM TaskSubmission r WHERE r.taskSubmissionId = ?1")
	TaskSubmission findByTaskSubmissionId(Long taskSubmissionId);

	@Query("SELECT t FROM TaskSubmission t WHERE t.taskRevisionId = ?1")
	List<TaskSubmission> findAllTaskSubmissionsForTaskRevision(Long taskRevisionId);

	@Query("SELECT max(ts.submissionNumber) + 1 FROM TaskSubmission ts WHERE ts.taskRevisionId = ?1")
	Integer getNextSubmissionNumber(Long taskRevisionId);
}
