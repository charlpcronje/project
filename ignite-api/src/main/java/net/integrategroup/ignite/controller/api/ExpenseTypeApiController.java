package net.integrategroup.ignite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.ExpenseType;
import net.integrategroup.ignite.persistence.service.ExpenseTypeService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/expense-type")
public class ExpenseTypeApiController {

	@Autowired
	ExpenseTypeService expenseTypeService;
	
	@GetMapping
	public ResponseEntity<?> getExpenseTypes() {
		try {
			List<ExpenseType> expenseTypes = expenseTypeService.findAll();
			
			return ResponseEntity.ok(expenseTypes);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
