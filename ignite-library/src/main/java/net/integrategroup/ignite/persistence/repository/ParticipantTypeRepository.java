package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ParticipantType;

@Repository
public interface ParticipantTypeRepository extends CrudRepository<ParticipantType, String> {

	@Override
	List<ParticipantType> findAll();

	@Query("SELECT pt FROM ParticipantType pt WHERE pt.participantTypeCode != 'INDIVIDUAL'")
	List<ParticipantType> findAllNonIndiv();


	@Query("SELECT pt FROM ParticipantType pt WHERE pt.participantTypeCode = ?1")
	ParticipantType findByParticipantTypeCode(String participantTypeCode);

}
