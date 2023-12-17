package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.AssignmentType;
import net.integrategroup.ignite.persistence.repository.AssignmentTypeRepository;

@Service
public class AssignmentTypeServiceImpl implements AssignmentTypeService {

	@Autowired
	AssignmentTypeRepository assignmentTypeRepository;

	@Override
	public AssignmentType save(AssignmentType assignmentType) {
		return assignmentTypeRepository.save(assignmentType);
	}

	//@Override
	//public void delete(ExpenseTypeParent expenseTypeParent) {
	//	expenseTypeParentRepository.delete(expenseTypeParent);
	//}

	@Override
	public List<AssignmentType> getAssignmentType() {
		return assignmentTypeRepository.getAssignmentType();
	}

	@Override
	public AssignmentType findByAssignmentTypeId(Long assignmentTypeId) {
		return assignmentTypeRepository.findByAssignmentTypeId(assignmentTypeId);
	}
}
