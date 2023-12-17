package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//import net.integrategroup.ignite.persistence.model.ContactPointView;
import net.integrategroup.ignite.persistence.model.ParticipantOffice;

@Repository
public interface ParticipantOfficeRepository extends CrudRepository<ParticipantOffice, Long> {

	@Query("SELECT po FROM ParticipantOffice po WHERE po.participantId = ?1")
	List<ParticipantOffice> findByParticipantId(Long participantId);

	@Query("SELECT po FROM ParticipantOffice po WHERE po.participantOfficeId = ?1")
	ParticipantOffice findByParticipantOfficeId(Long participantOfficeId);

	//@Query("SELECT cp " + "  FROM" + "    ContactPointView cp, ParticipantOfficeContactPoint pocp" + "  WHERE"
	//		+ "    cp.contactPointId = pocp.contactPointId AND" + "    pocp.participantOfficeId = ?1")
	//List<ContactPointView> findContactPointsByParticipantOfficeId(Long participantOfficeId);

}
