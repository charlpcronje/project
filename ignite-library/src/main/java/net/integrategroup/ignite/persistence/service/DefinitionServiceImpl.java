package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Definition;
import net.integrategroup.ignite.persistence.repository.DefinitionRepository;

@Service
public class DefinitionServiceImpl implements DefinitionService {

	@Autowired
	DefinitionRepository definitionRepository;

	@Override
	public Definition save(Definition definition) {
		return definitionRepository.save(definition);
	}

	//@Override
	//public void delete(ExpenseTypeParent expenseTypeParent) {
	//	expenseTypeParentRepository.delete(expenseTypeParent);
	//}

	@Override
	public List<Definition> getDefinition() {
		return definitionRepository.getDefinition();
	}

	@Override
	public List<Definition> findDefinitionNotLinked(Long genProcedureId) {
		return definitionRepository.findDefinitionNotLinked(genProcedureId);
	}

	@Override
	public Definition findByDefinitionId(Long definitionId) {
		return definitionRepository.findByDefinitionId(definitionId);
	}
}
