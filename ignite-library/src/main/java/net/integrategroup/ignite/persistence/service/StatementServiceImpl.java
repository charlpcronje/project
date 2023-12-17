package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.StatementTotalsDto;
import net.integrategroup.ignite.persistence.model.VStatement;
import net.integrategroup.ignite.persistence.repository.StatementRepository;

// @author Ingrid Marais

@Service
public class StatementServiceImpl implements StatementService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	StatementRepository statementRepository;


	// Statement Relationships:
	@Override
	public List<StatementTotalsDto> findStatementRelationshipsForParticipant(Long participantId, Date firstDay, Date lastDay) {
		return statementRepository.findStatementRelationshipsForParticipant(participantId, firstDay, lastDay);
	}

	@Override
	public List<VStatement> findStatementByRelationship(Long theParticipantId, Long theOtherParticipantId, Date fd, Date ld) {
		return statementRepository.findStatementByRelationship(theParticipantId, theOtherParticipantId, fd, ld);
	}

}

