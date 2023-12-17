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

import net.integrategroup.ignite.persistence.model.RecoverableExpense;
import net.integrategroup.ignite.persistence.model.VRecoverableExpense;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.RecoverableExpenseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/recoverable-expense")
public class RecoverableExpenseController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	RecoverableExpenseService recoverableExpenseService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping("/{agreementBetweenParticipantsId}")
	public ResponseEntity<?> findAllForAgreement(@PathVariable("agreementBetweenParticipantsId") Long agreementBetweenParticipantsId) {
		try {
			List<VRecoverableExpense> result = recoverableExpenseService.findAllForAgreement(agreementBetweenParticipantsId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveRecoverableExpense(@RequestBody RecoverableExpense recoverableExpense) {
		try {
			RecoverableExpense test = recoverableExpenseService
					.findByRecoverableExpenseId(recoverableExpense.getRecoverableExpenseId());

			if (test == null) {
				throw new Exception("Expense Agreement not found"); // Should not happen...
			}

			recoverableExpense = recoverableExpenseService.save(recoverableExpense);
			return ResponseEntity.ok(recoverableExpense);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveRecoverableExpenseNew(@RequestBody RecoverableExpense recoverableExpense) {
		try {
			RecoverableExpense test = recoverableExpenseService
					.findByRecoverableExpenseId(recoverableExpense.getRecoverableExpenseId());

			if (test != null) {
				throw new Exception("Expense Agreement already exists"); // Should not happen...
			}

			recoverableExpense = recoverableExpenseService.save(recoverableExpense);
			return ResponseEntity.ok(recoverableExpense);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteRecoverableExpense(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long recoverableExpenseId = mu.getAsLongOrNull(data, "recoverableExpenseId");
		String sql = "CALL ig_db.deleteRecoverableExpense(?);";

		System.out.println ("CALL ig_db.deleteRecoverableExpense(" + recoverableExpenseId+");");
		try {	//**//					
			Object[] params = {		
				recoverableExpenseId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
	}
}
