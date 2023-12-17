package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.TaskType;
import net.integrategroup.ignite.persistence.repository.TaskTypeRepository;

@Service
public class TaskTypeServiceImpl implements TaskTypeService {

	@Autowired
	TaskTypeRepository taskTypeRepository;

	@Override
	public TaskType save(TaskType taskType) {
		return taskTypeRepository.save(taskType);
	}

	//@Override
	//public void delete(ExpenseTypeParent expenseTypeParent) {
	//	expenseTypeParentRepository.delete(expenseTypeParent);
	//}

	@Override
	public List<TaskType> getTaskType() {
		return taskTypeRepository.getTaskType();
	}

	@Override
	public TaskType findByTaskTypeId(Long taskTypeId) {
		return taskTypeRepository.findByTaskTypeId(taskTypeId);
	}

	@Override
	public List<TaskType> findByAssignmentTypeId(Long assignmentTypeId) {
		return taskTypeRepository.findByAssignmentTypeId(assignmentTypeId);
	}
}
