package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.BankCard;
import net.integrategroup.ignite.persistence.model.VBankCard;
import net.integrategroup.ignite.persistence.service.BankCardService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/bank-card")
public class BankCardController {

	@Autowired
	BankCardService bankCardService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllBankCards() {
		try {
			List<BankCard> result = bankCardService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/participant/{participantId}")
	public ResponseEntity<?> findByParticipantId(@PathVariable("participantId") Long participantId) {
		try {
			List<VBankCard> result = bankCardService.findByParticipantId(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project/{projectId}")
	public ResponseEntity<?> findByProjectId(@PathVariable("projectId") Long projectId) {
		try {
			List<VBankCard> result = bankCardService.findByProjectId(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/participant-bank-details/{participantBankDetailsId}")
	public ResponseEntity<?> findByParticipantBankDetailsId(@PathVariable("participantBankDetailsId") Long participantBankDetailsId) {
		try {
			List<VBankCard> result = bankCardService.findByParticipantBankDetailsId(participantBankDetailsId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/individual/{individualId}")
	public ResponseEntity<?> findByIndividualId(@PathVariable("individualId") Long individualId) {
		try {
			List<VBankCard> result = bankCardService.findByIndividualId(individualId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveBankCard(@RequestBody BankCard bankCard) {
		try {

			BankCard test = bankCardService.findByBankCardId(bankCard.getBankCardId());
			if (test == null) {
				throw new Exception("Bank Card not found");
			}

			bankCard = bankCardService.save(bankCard);
			return ResponseEntity.ok(bankCard);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveBankCardNew(@RequestBody BankCard bankCard) {
		try {

			BankCard test = bankCardService.findByBankCardId(bankCard.getBankCardId());
			if (test != null) {
				throw new Exception("The Bank Card already exists");
			}

			bankCard = bankCardService.save(bankCard);
			return ResponseEntity.ok(bankCard);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteBankCard(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long bankCardId = mu.getAsLongOrNull(data, "bankCardId");
		String sql = "CALL ig_db.deleteBankCard(?);";

		System.out.println ("CALL ig_db.deleteBankCard(" + bankCardId +");");
		try {
			Object[] params = {
					bankCardId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
