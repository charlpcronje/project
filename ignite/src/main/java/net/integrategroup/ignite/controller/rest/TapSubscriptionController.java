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

import net.integrategroup.ignite.persistence.model.TapSubscription;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.TapSubscriptionService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;


@RestController
@RequestMapping("/rest/ignite/v1/tap-subscription")

public class TapSubscriptionController {

	@Autowired
	TapSubscriptionService tapSubscriptionService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping("/find-all")          //All records in the TapSubscription Table
	public ResponseEntity<?> findAll() {
		try {
			List<TapSubscription> result = tapSubscriptionService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/by-tap-subscription-code/{tapSubscriptionCode}")   //Find the record by the PrimaryKey
	public ResponseEntity<?> getByTapSubscriptionCode(@PathVariable("tapSubscriptionCode") String tapSubscriptionCode) {
		try {
			TapSubscription result = tapSubscriptionService.getByTapSubscriptionCode(tapSubscriptionCode);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()       //Save (update)
	public ResponseEntity<?> saveTapSubscription(@RequestBody TapSubscription tapSubscription) {
		try {
			TapSubscription test = tapSubscriptionService.getByTapSubscriptionCode(tapSubscription.getTapSubscriptionCode());
			if (test == null) {
				throw new Exception("TapSubscription not found");
			}
			tapSubscription = tapSubscriptionService.save(tapSubscription);
			return ResponseEntity.ok(tapSubscription);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")   //Save (new)
	public ResponseEntity<?> saveTapSubscriptionNew(@RequestBody TapSubscription tapSubscription) {
		try {
			TapSubscription test = tapSubscriptionService.getByTapSubscriptionCode(tapSubscription.getTapSubscriptionCode());
			if (test != null) {
				throw new Exception("The TapSubscription already exists");
			}
			tapSubscription = tapSubscriptionService.save(tapSubscription);
			return ResponseEntity.ok(tapSubscription);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")   //Delete a record, and insert into AuditTrial table
	public ResponseEntity<?> deleteTapSubscription(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		String tapSubscriptionCode = mu.getAsStringOrNull(data, "tapSubscriptionCode");
		String sql = "CALL ig_db.deleteTapSubscription(?);";

		String exampleSql = "CALL ig_db.deleteTapSubscription(" + tapSubscriptionCode + ");";
		System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

		try {	//**//					
			Object[] params = {		
					tapSubscriptionCode	
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