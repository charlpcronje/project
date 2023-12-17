package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ParticipantType;

public interface ParticipantTypeService {

	List<ParticipantType> findAll();

	List<ParticipantType> findAllNonIndiv();

	ParticipantType save(ParticipantType participantType);

	//void delete(String participantTypeCode);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

	ParticipantType findByParticipantTypeCode(String participantTypeCode);


}
