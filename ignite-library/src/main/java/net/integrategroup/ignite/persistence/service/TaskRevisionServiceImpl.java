package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.TaskRevision;
import net.integrategroup.ignite.persistence.repository.TaskRevisionRepository;


@Service
public class TaskRevisionServiceImpl implements TaskRevisionService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	TaskRevisionRepository taskRevisionRepository;

	@Override
	public TaskRevision findByTaskRevisionId(Long taskRevisionId) {
		return taskRevisionRepository.findByTaskRevisionId(taskRevisionId);
	}

	@Override
	public Integer getNextRevisionNumber(Long taskId) {
		return taskRevisionRepository.getNextRevisionNumber(taskId);
	}

	@Override
	public List<TaskRevision> findAll() {
		return taskRevisionRepository.findAll();
	}

	@Override
	public TaskRevision save(TaskRevision taskRevision) {
		return taskRevisionRepository.save(taskRevision);
	}

	@Override
	public List<TaskRevision> findAllTaskRevisionsForTask(Long taskId){
		return taskRevisionRepository.findAllTaskRevisionsForTask(taskId);
	}


}
