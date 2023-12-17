package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.TaskSubmission;


public interface TaskSubmissionService {

	List<TaskSubmission> findAll();

	TaskSubmission save(TaskSubmission taskSubmission);

	List<TaskSubmission> findAllTaskSubmissionsForTaskRevision(Long taskRevisionId);

	TaskSubmission findByTaskSubmissionId(Long taskSubmissionId);

	Integer getNextSubmissionNumber(Long taskRevisionId);

}
