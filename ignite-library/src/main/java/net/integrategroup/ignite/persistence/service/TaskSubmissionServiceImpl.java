package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.TaskSubmission;
import net.integrategroup.ignite.persistence.repository.TaskSubmissionRepository;


@Service
public class TaskSubmissionServiceImpl implements TaskSubmissionService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	TaskSubmissionRepository taskSubmissionRepository;


	@Override
	public Integer getNextSubmissionNumber(Long taskRevisionId) {
		return taskSubmissionRepository.getNextSubmissionNumber(taskRevisionId);
	}


	@Override
	public TaskSubmission findByTaskSubmissionId(Long taskSubmissionId) {
		return taskSubmissionRepository.findByTaskSubmissionId(taskSubmissionId);
	}


	@Override
	public List<TaskSubmission> findAll() {
		return taskSubmissionRepository.findAll();
	}

	@Override
	public TaskSubmission save(TaskSubmission taskSubmission) {
		return taskSubmissionRepository.save(taskSubmission);
	}

	@Override
	public List<TaskSubmission> findAllTaskSubmissionsForTaskRevision(Long taskRevisionId){
		return taskSubmissionRepository.findAllTaskSubmissionsForTaskRevision(taskRevisionId);
	}


}
