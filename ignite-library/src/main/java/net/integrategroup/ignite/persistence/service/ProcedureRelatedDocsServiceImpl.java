package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ProcedureRelatedDocs;
import net.integrategroup.ignite.persistence.repository.ProcedureRelatedDocsRepository;

@Service
public class ProcedureRelatedDocsServiceImpl implements ProcedureRelatedDocsService {

	@Autowired
	ProcedureRelatedDocsRepository procedureRelatedDocsRepository;

	@Override
	public List<ProcedureRelatedDocs> findAll() {
		return procedureRelatedDocsRepository.findAll();
	}

	@Override
	public ProcedureRelatedDocs save(ProcedureRelatedDocs procedureRelatedDocs) {
		return procedureRelatedDocsRepository.save(procedureRelatedDocs);
	}

	@Override
	public ProcedureRelatedDocs findByProcedureRelatedDocsId(Long procedureRelatedDocsId) {
		return procedureRelatedDocsRepository.findByProcedureRelatedDocsId(procedureRelatedDocsId);
	}

}
