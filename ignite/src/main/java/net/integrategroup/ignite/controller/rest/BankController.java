package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.Bank;
import net.integrategroup.ignite.persistence.model.Branch;
import net.integrategroup.ignite.persistence.service.BankService;
import net.integrategroup.ignite.persistence.service.BranchService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/bank")
public class BankController {

	@Autowired
	BankService bankService;

	@Autowired
	BranchService branchService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllBanks() {
		try {
			List<Bank> result = bankService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveBank(@RequestBody Bank bank) {
		try {

			Bank test = bankService.findByBankId(bank.getBankId());
			if (test == null) {
				throw new Exception("Bank not found");
			}

			bank = bankService.save(bank);
			return ResponseEntity.ok(bank);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveBankNew(@RequestBody Bank bank) {
		try {
			Bank test = bankService.findByBankId(bank.getBankId());
			if (test != null) {
				throw new Exception("The Bank already exists");
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
		try {
			Object[] params = {
					bankId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/branch")
	public ResponseEntity<?> saveBranch(@RequestBody Branch branch) {
		try {

			Branch test = branchService.findByBranchId(branch.getBranchId());
			if (test == null) {
				throw new Exception("Branch not found");
			}
			branch = branchService.save(branch);
			return ResponseEntity.ok(branch);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/branch/new")
	public ResponseEntity<?> saveBranchNew(@RequestBody Branch branch) {
		try {

			Branch test = branchService.findByBranchId(branch.getBranchId());
			if (test != null) {
				throw new Exception("The Branch already exists");
			}
			branch = branchService.save(branch);
			return ResponseEntity.ok(branch);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("branch/delete")
	public ResponseEntity<?> deleteBranch(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long branchId = mu.getAsLongOrNull(data, "branchId");
		String sql = "CALL ig_db.deleteBranch(?);";

		System.out.println ("CALL ig_db.deleteBranch(" + branchId +");");
		try {
			Object[] params = {
					branchId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

}
