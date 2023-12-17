package net.integrategroup.ignite.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.CompetencyLevel;
import net.integrategroup.ignite.persistence.repository.CompetencyLevelRepository;

@Service
public class CompetencyLevelServiceImpl implements CompetencyLevelService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	CompetencyLevelRepository competencyLevelRepository;

	@Override
	public CompetencyLevel findByCompetencyLevelId(Long competencyLevelId) {
		return competencyLevelRepository.findByCompetencyLevelId(competencyLevelId);
	}

	@Override
	public CompetencyLevel save(CompetencyLevel competencyLevel) {
		return competencyLevelRepository.save(competencyLevel);
	}

	@Override
	public List<CompetencyLevel> findAll() {
		return competencyLevelRepository.findAll();
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
