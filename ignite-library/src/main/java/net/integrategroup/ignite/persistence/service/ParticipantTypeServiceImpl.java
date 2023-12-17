package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ParticipantType;
import net.integrategroup.ignite.persistence.repository.ParticipantTypeRepository;

@Service
public class ParticipantTypeServiceImpl implements ParticipantTypeService {

	@Autowired
	ParticipantTypeRepository participantTypeRepository;

	@Override
	public List<ParticipantType> findAll() {
		return participantTypeRepository.findAll();
	}

	@Override
	public List<ParticipantType> findAllNonIndiv() {
		return participantTypeRepository.findAllNonIndiv();
	}

	@Override
	public ParticipantType save(ParticipantType participantType) {
		return participantTypeRepository.save(participantType);
	}

	//@Override
	//public void delete(String participantTypeCode) {
	//	ParticipantType participantType = participantTypeRepository
	//			.findByParticipantTypeCode(participantTypeCode);

	//	participantTypeRepository.delete(participantType);
	//}

	@Override
	public ParticipantType findByParticipantTypeCode(String participantTypeCode) {
		return participantTypeRepository.findByParticipantTypeCode(participantTypeCode);
	}

}
