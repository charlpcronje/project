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

import net.integrategroup.ignite.persistence.model.TaxDeductableCategory;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.TaxDeductableCategoryService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/tax-deductable-category")
public class TaxDeductableCategoryController {

	@Autowired
	public TaxDeductableCategoryService taxDeductableCategoryService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> findAll() {
		try {
			List<TaxDeductableCategory> result = taxDeductableCategoryService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveTaxDeductableCategory(@RequestBody TaxDeductableCategory taxDeductableCategory) {
		try {

			TaxDeductableCategory test = taxDeductableCategoryService.findByTaxDeductableCategoryId(taxDeductableCategory.getTaxDeductableCategoryId());
			if (test == null) {
				throw new Exception("Tax Deductable Category not found");
			}

			taxDeductableCategory = taxDeductableCategoryService.save(taxDeductableCategory);
			return ResponseEntity.ok(taxDeductableCategory);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveTaxDeductableCategoryNew(@RequestBody TaxDeductableCategory taxDeductableCategory) {
		try {

			TaxDeductableCategory test = taxDeductableCategoryService.findByTaxDeductableCategoryId(taxDeductableCategory.getTaxDeductableCategoryId());
			if (test != null) {
				throw new Exception("The Tax Deductable Category already exists");
			}

			taxDeductableCategory = taxDeductableCategoryService.save(taxDeductableCategory);
			return ResponseEntity.ok(taxDeductableCategory);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteTaxDeductableCategory(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long taxDeductableCategoryId = mu.getAsLongOrNull(data, "taxDeductableCategoryId");
		String sql = "CALL ig_db.deleteTaxDeductableCategory(?);";

		System.out.println ("CALL ig_db.deleteTaxDeductableCategory(" + taxDeductableCategoryId +");");
		try {	//**//					
			Object[] params = {		
					taxDeductableCategoryId	
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
