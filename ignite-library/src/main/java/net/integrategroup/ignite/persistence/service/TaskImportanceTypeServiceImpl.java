package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.TaskImportanceType;
import net.integrategroup.ignite.persistence.repository.TaskImportanceTypeRepository;

@Service
public class TaskImportanceTypeServiceImpl implements TaskImportanceTypeService {

	@Autowired
	TaskImportanceTypeRepository taskImportanceTypeRepository;

	@Override
	public TaskImportanceType save(TaskImportanceType taskImportanceType) {
		return taskImportanceTypeRepository.save(taskImportanceType);
	}

	//@Override
	//public void delete(ExpenseTypeParent expenseTypeParent) {
	//	expenseTypeParentRepository.delete(expenseTypeParent);
	//}

	@Override
	public List<TaskImportanceType> getTaskImportanceType() {
		return taskImportanceTypeRepository.getTaskImportanceType();
	}

	@Override
	public TaskImportanceType findByTaskImportanceTypeId(Long taskImportanceTypeId) {
		return taskImportanceTypeRepository.findByTaskImportanceTypeId(taskImportanceTypeId);
	}
}
