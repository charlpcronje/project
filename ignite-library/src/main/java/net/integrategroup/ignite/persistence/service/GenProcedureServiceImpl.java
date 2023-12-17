package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.GenProcedure;
import net.integrategroup.ignite.persistence.model.VPersonResponsible;
import net.integrategroup.ignite.persistence.model.VProcedureDefinition;
import net.integrategroup.ignite.persistence.model.VProcedureRelatedDocs;
import net.integrategroup.ignite.persistence.repository.GenProcedureRepository;
import net.integrategroup.ignite.persistence.repository.VPersonResponsibleRepository;
import net.integrategroup.ignite.persistence.repository.VProcedureDefinitionRepository;
import net.integrategroup.ignite.persistence.repository.VProcedureRelatedDocsRepository;


@Service
public class GenProcedureServiceImpl implements GenProcedureService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	GenProcedureRepository genProcedureRepository;

	@Autowired
	VProcedureDefinitionRepository vProcedureDefinitionRepository;

	@Autowired
	VPersonResponsibleRepository vPersonResponsibleRepository;

	@Autowired
	VProcedureRelatedDocsRepository vProcedureRelatedDocsRepository;


	@Override
	public Integer getNextGenProcedureNumber(Long serviceDisciplineId) {
		return genProcedureRepository.getNextGenProcedureNumber(serviceDisciplineId);
	}


	@Override
	public GenProcedure findByGenProcedureId(Long genProcedureId) {
		return genProcedureRepository.findByGenProcedureId(genProcedureId);
	}


	@Override
	public List<GenProcedure> findAll() {
		return genProcedureRepository.findAll();
	}

	@Override
	public GenProcedure save(GenProcedure genProcedure) {
		return genProcedureRepository.save(genProcedure);
	}

	@Override
	public List<GenProcedure> findAllGenProceduresForServiceDisciplineId(Long serviceDisciplineId){
		return genProcedureRepository.findAllGenProceduresForServiceDisciplineId(serviceDisciplineId);
	}


	@Override
	public List<VProcedureDefinition> findAllProcedureDefinition() {
		return vProcedureDefinitionRepository.findAllProcedureDefinition();
	}

	@Override
	public List<VProcedureDefinition> findByVPDbyDefinitionId(Long definitionId) {
		return vProcedureDefinitionRepository.findByVPDbyDefinitionId(definitionId);
	}

	@Override
	public List<VProcedureDefinition> findByVPDbyProcedureId(Long genProcedureId) {
		return vProcedureDefinitionRepository.findByVPDbyProcedureId(genProcedureId);
	}


	@Override
	public List<VPersonResponsible> findAllPersonResponsible() {
		return vPersonResponsibleRepository.findAllPersonResponsible();
	}

	@Override
	public List<VPersonResponsible> findPRbyProcedureId(Long genProcedureId) {
		return vPersonResponsibleRepository.findPRbyProcedureId(genProcedureId);
	}


	@Override
	public List<VProcedureRelatedDocs> findAllProcedureRelatedDocs() {
		return vProcedureRelatedDocsRepository.findAllProcedureRelatedDocs();
	}

	@Override
	public List<VProcedureRelatedDocs> findPRDbyProcedureId(Long genProcedureId) {
		return vProcedureRelatedDocsRepository.findPRDbyProcedureId(genProcedureId);
	}

}
