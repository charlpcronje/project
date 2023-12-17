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

import net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsDto;
import net.integrategroup.ignite.persistence.model.ParticipantExpenseCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.VPpExpenseRateUplineRecursive;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.FinancialsService;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/financials")


public class FinancialsController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	FinancialsService financialsService;

	@Autowired
	SecurityUtils securityUtils;


	@GetMapping("/relationships-unique/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findAgreementRelationshipsForParticipant(ModelMap modelMap,
															@PathVariable("participantId") Long participantId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay") Long lastDay
															) {
		try {
			Date fd = new Date(firstDay); // Dates to and db
			Date ld = new Date(lastDay);
			List<ParticipantTimeCostTotalsDto> result = financialsService.findAgreementRelationshipsForParticipant(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Time Related Costs
	// Table 2

	// ParticipantTimeCostTotalsPerProjectDto probleempie
	@GetMapping("/payer-ben-time-cost/{agreementParticipantIdPayer}/{agreementParticipantIdBeneficiary}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findPayerBenAgreementTimeCost(ModelMap modelMap,
									@PathVariable("agreementParticipantIdPayer") Long agreementParticipantIdPayer,
									@PathVariable("agreementParticipantIdBeneficiary") Long agreementParticipantIdBeneficiary,
									@PathVariable(name = "firstDay") Long firstDay,
									@PathVariable(name = "lastDay") Long lastDay
									) {
		try {
			Date fd = new Date(firstDay); // Dates to and db
			Date ld = new Date(lastDay);
			List<ParticipantTimeCostTotalsPerProjectDto> result = financialsService.findPayerBenAgreementTimeCost(agreementParticipantIdPayer,agreementParticipantIdBeneficiary, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	// Expense Claim Costs
	// Table 1
	@GetMapping("/relationships-unique-expense-claims/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findAgreementRelationshipsForParticipantExpenseClaims(ModelMap modelMap,
															@PathVariable("participantId") Long participantId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay") Long lastDay
															) {
		try {
			Date fd = new Date(firstDay); // Dates to and db
			Date ld = new Date(lastDay);
			List<ParticipantExpenseCostTotalsDto> result = financialsService.findAgreementRelationshipsForParticipantExpenseClaims(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Table 2
	@GetMapping("/payer-ben-expense-cost/{participantIdContracting}/{participantIdContracted}/{firstDay}/{lastDay}")
	public ResponseEntity<?> findPayerBenAgreementExpenseCost(ModelMap modelMap,
									@PathVariable("participantIdContracting") Long participantIdContracting,
									@PathVariable("participantIdContracted") Long participantIdContracted,
									@PathVariable(name = "firstDay") Long firstDay,
									@PathVariable(name = "lastDay") Long lastDay
									) {
		try {
			Date fd = new Date(firstDay); // Dates to and db
			Date ld = new Date(lastDay);
			List<ParticipantExpenseCostTotalsPerProjectDto> result = financialsService.findPayerBenAgreementExpenseCost(participantIdContracting,participantIdContracted, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Table 3
	@GetMapping("/contracting-contracted-expenses/{expenseTypeId}/{firstDay}/{lastDay}/{participantIdContracting}/{participantIdContracted}")
	public ResponseEntity<?> findContractingContractedExpenseCost(ModelMap modelMap,
									@PathVariable(name = "expenseTypeId") Long expenseTypeId,
									@PathVariable(name = "firstDay") Long firstDay,
									@PathVariable(name = "lastDay") Long lastDay,
									@PathVariable(name = "participantIdContracting") Long participantIdContracting,
									@PathVariable(name = "participantIdContracted") Long participantIdContracted
									) {
		try {
			Date fd = new Date(firstDay); // Dates to and db
			Date ld = new Date(lastDay);
			List<VPpExpenseRateUplineRecursive> result = financialsService.findContractingContractedExpenseCost(expenseTypeId,fd, ld,participantIdContracting,participantIdContracted);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
