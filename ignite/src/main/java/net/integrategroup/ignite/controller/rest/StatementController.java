package net.integrategroup.ignite.controller.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.StatementTotalsDto;
import net.integrategroup.ignite.persistence.model.VStatement;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.StatementService;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/statement")


public class StatementController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	StatementService statementService;

	@Autowired
	SecurityUtils securityUtils;


	@GetMapping("/relationships-unique/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findStatementRelationshipsForParticipant(ModelMap modelMap,
															@PathVariable("participantId") Long participantId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay") Long lastDay
															) {
		try {
			Date fd = new Date(firstDay); // Dates to and db
			Date ld = new Date(lastDay);
			List<StatementTotalsDto> result = statementService.findStatementRelationshipsForParticipant(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Table 2
	@GetMapping("/per-relationship/{theParticipantId}/{theOtherParticipantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findStatementByRelationship(ModelMap modelMap,
									@PathVariable(name = "theParticipantId") Long theParticipantId,
									@PathVariable(name = "theOtherParticipantId") Long theOtherParticipantId,
									@PathVariable(name = "firstDay") Long firstDay,
									@PathVariable(name = "lastDay") Long lastDay
									) {
		try {
			Date fd = new Date(firstDay); // Dates to and db
			Date ld = new Date(lastDay);
			List<VStatement> result = statementService.findStatementByRelationship(theParticipantId,theOtherParticipantId,fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
