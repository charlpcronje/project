package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.StatementTotalsDto;
import net.integrategroup.ignite.persistence.model.VStatement;

public interface StatementService {

	// Statement Relationships:
	List<StatementTotalsDto> findStatementRelationshipsForParticipant(Long participantId, Date firstDay, Date lastDay);

	List<VStatement> findStatementByRelationship(Long theParticipantId, Long theOtherParticipantId, Date fd, Date ld);

}
