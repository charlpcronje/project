package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Task;

/**
 * @author Tony De Buys
 *
 */
public interface TaskService {

	Task findByTaskId(Long taskId);

	List<Task> findAll();

	Task save(Task task);

	Integer getMaxRevisionNumber(Long taskId);

}
