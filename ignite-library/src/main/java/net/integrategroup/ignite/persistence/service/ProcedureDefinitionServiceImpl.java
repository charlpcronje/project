package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ProcedureDefinition;
import net.integrategroup.ignite.persistence.repository.ProcedureDefinitionRepository;

@Service
public class ProcedureDefinitionServiceImpl implements ProcedureDefinitionService {

	@Autowired
	ProcedureDefinitionRepository procedureDefinitionRepository;

	@Override
	public List<ProcedureDefinition> findAll() {
		return procedureDefinitionRepository.findAll();
	}

	@Override
	public ProcedureDefinition save(ProcedureDefinition procedureDefinition) {
		return procedureDefinitionRepository.save(procedureDefinition);
	}

	@Override
	public ProcedureDefinition findByProcedureDefinitionId(Long procedureDefinitionId) {
		return procedureDefinitionRepository.findByProcedureDefinitionId(procedureDefinitionId);
	}

}
