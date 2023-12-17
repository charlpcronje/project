package net.integrategroup.ignite.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.RemunerationInterval;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.RemunerationIntervalService;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/remuneration-interval")
public class RemunerationIntervalController {

	@Autowired
	RemunerationIntervalService remunerationIntervalService;


	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAll() {
		try {
			List<RemunerationInterval> result = remunerationIntervalService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveRemunerationInterval(@RequestBody RemunerationInterval remunerationInterval) {
		try {

			RemunerationInterval test = remunerationIntervalService.findByRemunerationIntervalCode(remunerationInterval.getRemunerationIntervalCode());
			if (test == null) {
				throw new Exception("Remuneration Interval not found");
			}

			remunerationInterval = remunerationIntervalService.save(remunerationInterval);
			return ResponseEntity.ok(remunerationInterval);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/*
	@PostMapping("/new")
	public ResponseEntity<?> saveBankNew(@RequestBody Bank bank) {
		try {
			Bank test = bankService.findByBankId(bank.getBankId());
			if (test != null) {
				throw new Exception("The Bank Code already exists");
			}

			bank = bankService.save(bank);
			return ResponseEntity.ok(bank);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteBank(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long bankId = mu.getAsLongOrNull(data, "bankId");
		String sql = "CALL ig_db.deleteBank(?);";

		System.out.println ("CALL ig_db.deleteBank(" + bankId +");");
		try (CallableStatement cstm = databaseService.prepareCall(sql)) {
			cstm.setLong(1, bankId);
			cstm.execute();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	*/

}
