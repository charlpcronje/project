package net.integrategroup.ignite.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ProcedureStatus;
import net.integrategroup.ignite.persistence.repository.ProcedureStatusRepository;

@Service
public class ProcedureStatusServiceImpl implements ProcedureStatusService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	ProcedureStatusRepository procedureStatusRepository;

	@Override
	public ProcedureStatus findByProcedureStatusId(Long procedureStatusId) {
		return procedureStatusRepository.findByProcedureStatusId(procedureStatusId);
	}

	@Override
	public ProcedureStatus save(ProcedureStatus procedureStatus) {
		return procedureStatusRepository.save(procedureStatus);
	}

	@Override
	public List<ProcedureStatus> findAll() {
		return procedureStatusRepository.findAll();
	}
	//@Override
	//public void delete(Long participantOfficeId) {
	//	ParticipantOffice participantOffice = participantOfficeRepository.findByParticipantOfficeId(participantOfficeId);

	//	participantOfficeRepository.delete(participantOffice);
	//}

//	@Override
//	public void delete(Long participantOfficeId) {
//		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("ig_db.deleteParticipantOffice");
//		storedProcedure.registerStoredProcedureParameter(0, Long.class, ParameterMode.IN);     // participantOfficeId
//
//		storedProcedure.setParameter(0, participantOfficeId);
//
//		storedProcedure.execute();
//	}

}
