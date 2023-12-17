package net.integrategroup.ignite.persistence.service;

import java.util.List;

//import net.integrategroup.ignite.persistence.model.ContactPointView;
import net.integrategroup.ignite.persistence.model.ParticipantOffice;

public interface ParticipantOfficeService {

	ParticipantOffice findByParticipantOfficeId(Long participantOfficeId);

	List<ParticipantOffice> findByParticipantId(Long participantId);

	//List<ContactPointView> findContactPointsByParticipantOfficeId(Long participantOfficeId);

	ParticipantOffice save(ParticipantOffice participantOffice);

	//void delete(Long participantOfficeId);

}
