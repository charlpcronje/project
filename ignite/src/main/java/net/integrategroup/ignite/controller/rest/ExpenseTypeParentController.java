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

import net.integrategroup.ignite.persistence.model.ExpenseTypeParent;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ExpenseTypeParentService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/expense-type-parent")
public class ExpenseTypeParentController {

	@Autowired
	ExpenseTypeParentService expenseTypeParentService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getExpenseTypeParents() {
		try {
			List<ExpenseTypeParent> result = expenseTypeParentService.getExpenseTypeParents();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveExpenseTypeParent(@RequestBody ExpenseTypeParent expenseTypeParent) {
		try {

			ExpenseTypeParent test = expenseTypeParentService
					.findByExpenseTypeParentId(expenseTypeParent.getExpenseTypeParentId());
			if (test == null) {
				throw new Exception("Expense Type Parent not found");
			}

			expenseTypeParent = expenseTypeParentService.save(expenseTypeParent);
			return ResponseEntity.ok(expenseTypeParent);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveExpenseTypeParentNew(@RequestBody ExpenseTypeParent expenseTypeParent) {
		try {

			ExpenseTypeParent test = expenseTypeParentService
					.findByExpenseTypeParentId(expenseTypeParent.getExpenseTypeParentId());
			if (test != null) {
				throw new Exception("Expense Type Parent already exists");
			}

			expenseTypeParent = expenseTypeParentService.save(expenseTypeParent);
			return ResponseEntity.ok(expenseTypeParent);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteExpenseTypeParent(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long expenseTypeParentId = mu.getAsLongOrNull(data, "expenseTypeParentId");
		String sql = "CALL ig_db.deleteExpenseTypeParent(?);";

		System.out.println ("CALL ig_db.deleteExpenseTypeParent(" + expenseTypeParentId +");");
		try {
			Object[] params = {
					expenseTypeParentId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}
