package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.TaskRevision;


public interface TaskRevisionService {

	List<TaskRevision> findAll();

	TaskRevision save(TaskRevision taskRevision);

	List<TaskRevision> findAllTaskRevisionsForTask(Long taskId);

	TaskRevision findByTaskRevisionId(Long taskRevisionId);

	Integer getNextRevisionNumber(Long taskId);

}
