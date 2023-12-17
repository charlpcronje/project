package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ProcedureStatus;

public interface ProcedureStatusService {

	ProcedureStatus findByProcedureStatusId(Long procedureStatusId);

	List<ProcedureStatus> findAll();

	ProcedureStatus save(ProcedureStatus procedureStatus);

	//void delete(Long procedureStatusId);

}
