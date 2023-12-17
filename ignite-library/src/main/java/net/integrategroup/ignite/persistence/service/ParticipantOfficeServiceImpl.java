package net.integrategroup.ignite.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import net.integrategroup.ignite.persistence.model.ContactPointView;
import net.integrategroup.ignite.persistence.model.ParticipantOffice;
import net.integrategroup.ignite.persistence.repository.ParticipantOfficeRepository;

@Service
public class ParticipantOfficeServiceImpl implements ParticipantOfficeService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	ParticipantOfficeRepository participantOfficeRepository;


	@Override
	public ParticipantOffice findByParticipantOfficeId(Long participantOfficeId) {
		return participantOfficeRepository.findByParticipantOfficeId(participantOfficeId);
	}

	@Override
	public List<ParticipantOffice> findByParticipantId(Long participantId) {
		return participantOfficeRepository.findByParticipantId(participantId);
	}

	//@Override
	//public List<ContactPointView> findContactPointsByParticipantOfficeId(Long participantOfficeId) {
	//	return participantOfficeRepository.findContactPointsByParticipantOfficeId(participantOfficeId);
	//}

	@Override
	public ParticipantOffice save(ParticipantOffice participantOffice) {
		return participantOfficeRepository.save(participantOffice);
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
