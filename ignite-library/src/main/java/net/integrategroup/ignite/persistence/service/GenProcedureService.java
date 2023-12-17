package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.GenProcedure;
import net.integrategroup.ignite.persistence.model.VPersonResponsible;
import net.integrategroup.ignite.persistence.model.VProcedureDefinition;
import net.integrategroup.ignite.persistence.model.VProcedureRelatedDocs;


public interface GenProcedureService {

	List<GenProcedure> findAll();

	GenProcedure save(GenProcedure genProcedure);

	List<GenProcedure> findAllGenProceduresForServiceDisciplineId(Long serviceDisciplineId);

	GenProcedure findByGenProcedureId(Long genProcedureId);

	Integer getNextGenProcedureNumber(Long serviceDisciplineId);


	List<VProcedureDefinition> findAllProcedureDefinition();

	List<VProcedureDefinition> findByVPDbyDefinitionId(Long definitionId);

	List<VProcedureDefinition> findByVPDbyProcedureId(Long genProcedureId);

	List<VPersonResponsible> findAllPersonResponsible();

	List<VPersonResponsible> findPRbyProcedureId(Long genProcedureId);


	List<VProcedureRelatedDocs> findAllProcedureRelatedDocs();

	List<VProcedureRelatedDocs> findPRDbyProcedureId(Long genProcedureId);

}
