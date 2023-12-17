package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
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

import net.integrategroup.ignite.persistence.model.ExpenseType;
import net.integrategroup.ignite.persistence.model.UnitType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ExpenseTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/expense-type")
public class ExpenseTypeController {

	@Autowired
	ExpenseTypeService expenseTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> findAll() {
		try {
			List<ExpenseType> result = expenseTypeService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/by-parent/{expenseTypeParentId}")
	public ResponseEntity<?> findByExpenseTypeParentId(
			@PathVariable("expenseTypeParentId") Long expenseTypeParentId,ModelMap modelMap) {
		try {
			List<ExpenseType> result = expenseTypeService.findByExpenseTypeParentId(expenseTypeParentId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/{expenseTypeId}")
	public ResponseEntity<?> findByExpenseTypeId(
			@PathVariable("expenseTypeId") Long expenseTypeId, ModelMap modelMap) {
		try {
			ExpenseType result = expenseTypeService.findByExpenseTypeId(expenseTypeId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Hier haal ons net een rekord van die db
	@GetMapping("/unit/{expenseTypeId}")
	public ResponseEntity<?> findUnitByExpenseTypeId(@PathVariable("expenseTypeId") Long expenseTypeId,
			ModelMap modelMap) {
		try {
			UnitType result = expenseTypeService.findUnitByExpenseTypeId(expenseTypeId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveExpenseType(@RequestBody ExpenseType expenseType) {
		try {
			ExpenseType test = expenseTypeService.findByExpenseTypeId(expenseType.getExpenseTypeId());
			if (test == null) {
				throw new Exception("Expense Type not found");
			}

			expenseType = expenseTypeService.save(expenseType);
			return ResponseEntity.ok(expenseType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveExpenseTypeNew(@RequestBody ExpenseType expenseType) {
		try {
			ExpenseType test = expenseTypeService.findByExpenseTypeParentIdAndId(expenseType.getExpenseTypeParentId(),
					expenseType.getExpenseTypeId());
			if (test != null) {
				throw new Exception("The Expense Type already exists");
			}

			expenseType = expenseTypeService.save(expenseType);
			return ResponseEntity.ok(expenseType);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteExpenseType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long expenseTypeId = mu.getAsLongOrNull(data, "expenseTypeId");
		String sql = "CALL ig_db.deleteExpenseType(?);";

		System.out.println ("CALL ig_db.deleteExpenseType(" + expenseTypeId +");");
		try {
			Object[] params = {
					expenseTypeId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/not-allowance")
	public ResponseEntity<?> findAllExceptAllowance() {
		try {
			List<ExpenseType> result = expenseTypeService.findAllExceptAllowance();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/allowance-only")
	public ResponseEntity<?> findOnlyAllowanceExpenseTypes() {
		try {
			List<ExpenseType> result = expenseTypeService.findOnlyAllowanceExpenseTypes();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
