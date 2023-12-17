package net.integrategroup.ignite.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.StatementTotalsDto;
import net.integrategroup.ignite.persistence.model.VStatement;

@Repository
public interface StatementRepository extends CrudRepository<VStatement, Long> {

	// Statement
	// Table 1: Relationships available
	@Query("SELECT new net.integrategroup.ignite.persistence.model.StatementTotalsDto( "
			+ "	v.theParticipantId, "
			+ "	v.theParticipant,"
			+ "	v.theOtherParticipantId, "
			+ "	v.theOtherParticipant,"
			+ "	SUM(v.theAmount)) "
			+ " FROM VStatement v "
			+ " WHERE v.theParticipantId = ?1"
			+ " AND v.theDate BETWEEN ?2 AND ?3 "
			+ " GROUP BY "
			+ "	v.theParticipantId, "
			+ "	v.theParticipant,"
			+ "	v.theOtherParticipantId, "
			+ "	v.theOtherParticipant")

	List<StatementTotalsDto> findStatementRelationshipsForParticipant(Long participantId, Date firstDay, Date lastDay);

	// Table 2 Statement
	@Query("SELECT v "
			+ " FROM VStatement v"
			+ " WHERE  	v.theParticipantId = ?1 "
			+ " AND 	v.theOtherParticipantId = ?2 "
			+ " AND 	v.theDate BETWEEN ?3 AND ?4 "
			+ " ORDER BY 	v.theDate")
	List<VStatement> findStatementByRelationship(Long theParticipantId, Long theOtherParticipantId, Date fd, Date ld);

}
