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

import net.integrategroup.ignite.persistence.model.AccountType;
import net.integrategroup.ignite.persistence.service.AccountTypeService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/account-type")
public class AccountTypeController {


	@Autowired
	AccountTypeService accountTypeService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping()
	public ResponseEntity<?> getAccountType() {
		try {
			List<AccountType> result = accountTypeService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping()
	public ResponseEntity<?> saveAccountType(@RequestBody AccountType accountType) {
		try {
			AccountType test = accountTypeService.findByAccountTypeId(accountType.getAccountTypeId());
			if (test == null) {
				throw new Exception("Account Type not found");
			}

			accountType = accountTypeService.save(accountType);

			return ResponseEntity.ok(accountType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveAccountTypeNew(@RequestBody AccountType accountType) {
		try {
			AccountType test = accountTypeService.findByAccountTypeId(accountType.getAccountTypeId());
			if (test != null) {
				throw new Exception("Account Type already exists");
			}

			accountType = accountTypeService.save(accountType);

			return ResponseEntity.ok(accountType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteAccountType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long accountTypeId = mu.getAsLongOrNull(data, "accountTypeId");
		String sql = "CALL ig_db.deleteAccountType(?);";

		System.out.println ("CALL ig_db.deleteAccountType(" + accountTypeId+");");
		
		try {
			Object[] params = {
					accountTypeId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}