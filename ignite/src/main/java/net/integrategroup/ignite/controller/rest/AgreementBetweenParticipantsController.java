package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.AgreementBetweenParticipants;
import net.integrategroup.ignite.persistence.model.AgreementRelationshipDto;
import net.integrategroup.ignite.persistence.model.ParticipantTimeCostTotalsPerProjectDto;
import net.integrategroup.ignite.persistence.model.RecoverableExpenseAgreementDto;
import net.integrategroup.ignite.persistence.model.VAgreementBetweenParticipants;
import net.integrategroup.ignite.persistence.model.VPPIndividualRatesUpline;
import net.integrategroup.ignite.persistence.model.VRecoverableExpenseAgreement;
import net.integrategroup.ignite.persistence.service.AgreementBetweenParticipantsService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ProjectParticipantService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/agreement-between-participants")


public class AgreementBetweenParticipantsController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	AgreementBetweenParticipantsService agreementBetweenParticipantsService;

	@Autowired
	ProjectParticipantService projectParticipantService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping("/project/{projectId}")
	public ResponseEntity<?> findAllForProject(@PathVariable("projectId") Long projectId,
			ModelMap modelMap) {
		try {
			List<VAgreementBetweenParticipants> result = agreementBetweenParticipantsService.findAllForProject(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project-participant/{projectParticipantId}")
	public ResponseEntity<?> findAllForProjectParticipant(@PathVariable("projectParticipantId") Long projectParticipantId,
			ModelMap modelMap) {
		try {
			List<VAgreementBetweenParticipants> result = agreementBetweenParticipantsService.findAllForProjectParticipant(projectParticipantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/remuneration/{projectId}")
	public ResponseEntity<?> findAllTimeCostAgreementsForProject(@PathVariable("projectId") Long projectId,
			ModelMap modelMap) {
		try {
			List<VAgreementBetweenParticipants> result = agreementBetweenParticipantsService.findAllTimeCostAgreementsForProject(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/recoverable-expense/{projectId}")
	public ResponseEntity<?> findAllExpenseAgreementsForProject(@PathVariable("projectId") Long projectId,
			ModelMap modelMap) {
		try {
			List<VRecoverableExpenseAgreement> result = agreementBetweenParticipantsService.findAllExpenseAgreementsForProject(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/distinct-recoverable-expense/{projectId}")
	public ResponseEntity<?> findDisctinctExpenseAgreementsForProject(@PathVariable("projectId") Long projectId,
			ModelMap modelMap) {
		try {
			List<RecoverableExpenseAgreementDto> result = agreementBetweenParticipantsService.findDisctinctExpenseAgreementsForProject(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


//	@GetMapping("/participant/{projectParticipantId}")
//	public ResponseEntity<?> findAllForProjectParticipant(@PathVariable("projectParticipantId") Long projectParticipantId,
//			ModelMap modelMap) {
//		try {
//			List<AgreementBetweenParticipants> result = agreementBetweenParticipantsService.findAllForProjectParticipant(projectParticipantId);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@PostMapping()
	public ResponseEntity<?> saveAgreementBetweenParticipants(@RequestBody AgreementBetweenParticipants agreementBetweenParticipants) {
		try {
			AgreementBetweenParticipants test = agreementBetweenParticipantsService
					.findByAgreementBetweenParticipantsId(agreementBetweenParticipants.getAgreementBetweenParticipantsId());

			if (test == null) {
				throw new Exception("Agreement Between Participants Account Type not found"); // Should not happen...
			}

			agreementBetweenParticipants = agreementBetweenParticipantsService.save(agreementBetweenParticipants);
			return ResponseEntity.ok(agreementBetweenParticipants);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveAgreementBetweenParticipantsNew(@RequestBody AgreementBetweenParticipants agreementBetweenParticipants) {
		try {
			AgreementBetweenParticipants test = agreementBetweenParticipantsService
					.findByAgreementBetweenParticipantsId(agreementBetweenParticipants.getAgreementBetweenParticipantsId());

			if (test != null) {
				throw new Exception("Agreement Between Participants Account Type already exists"); // Should not happen...
			}

			agreementBetweenParticipants = agreementBetweenParticipantsService.save(agreementBetweenParticipants);
			return ResponseEntity.ok(agreementBetweenParticipants);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteAgreementBetweenParticipants(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long agreementBetweenParticipantsId = mu.getAsLongOrNull(data, "agreementBetweenParticipantsId");
		String sql = "CALL ig_db.deleteAgreementBetweenParticipants(?);";

		System.out.println ("CALL ig_db.deleteAgreementBetweenParticipants(" + agreementBetweenParticipantsId+");");
		try {
			Object[] params = {
					agreementBetweenParticipantsId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
	}
	
	
	@GetMapping("/project-unique/{projectId}")
	public ResponseEntity<?> findAgreementRelationshipsForProject(@PathVariable("projectId") Long projectId,
			ModelMap modelMap) {
		try {
			List<AgreementRelationshipDto> result = agreementBetweenParticipantsService.findAgreementRelationshipsForProject(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

// ParticipantTimeCostTotalsPerProjectDto probleempie
		@GetMapping("/project-time-cost/{agreementBetweenParticipantsId}")
	public ResponseEntity<?> findProjectAgreementTimeCost(@PathVariable("agreementBetweenParticipantsId") Long agreementBetweenParticipantsId,
			ModelMap modelMap) {
		try {
			List<ParticipantTimeCostTotalsPerProjectDto> result = agreementBetweenParticipantsService.findProjectAgreementTimeCost(agreementBetweenParticipantsId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project-time-cost-detail/{agreementBetweenParticipantsId}/{projectSdId}/{lastDay}")
	public ResponseEntity<?> findProjectAgreementTimeCostDetail(ModelMap modelMap,
				@PathVariable("agreementBetweenParticipantsId") Long agreementBetweenParticipantsId,
				@PathVariable("projectSdId") Long projectSdId,
				@PathVariable(name = "lastDay") Long lastDay) {

		try {
			Date ld = new Date(lastDay + (24*60*60*1000) - 1); // 1 ms before midnight
			List<VPPIndividualRatesUpline> result = agreementBetweenParticipantsService.findProjectAgreementTimeCostDetail(agreementBetweenParticipantsId, projectSdId, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}
