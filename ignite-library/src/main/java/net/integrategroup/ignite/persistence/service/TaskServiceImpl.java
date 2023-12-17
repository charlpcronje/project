package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Task;
import net.integrategroup.ignite.persistence.repository.TaskRepository;


@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	TaskRepository taskRepository;

	@Override
	public Task findByTaskId(Long taskId) {
		return taskRepository.findByTaskId(taskId);
	}

	@Override
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public Integer getMaxRevisionNumber(Long taskId) {
		return taskRepository.getMaxRevisionNumber(taskId);
	}

}
