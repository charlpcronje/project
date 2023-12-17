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

import net.integrategroup.ignite.persistence.model.MedicalInsuranceCompany;
import net.integrategroup.ignite.persistence.model.MedicalInsurancePlan;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.MedicalInsuranceCompanyService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/medical-insurance-company")
public class MedicalInsuranceCompanyController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	MedicalInsuranceCompanyService medicalInsuranceCompanyService;

	@GetMapping()
	public ResponseEntity<?> findAll() {
		try {
			List<MedicalInsuranceCompany> data = medicalInsuranceCompanyService.findAll();
			return ResponseEntity.ok().body(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/plans/{id}")
	public ResponseEntity<?> getPlans(@PathVariable("id") Long medicalInsuranceCompanyId) {
		try {
			List<MedicalInsurancePlan> data = medicalInsuranceCompanyService.getPlans(medicalInsuranceCompanyId);
			return ResponseEntity.ok().body(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveMedicalInsuranceCompany(@RequestBody MedicalInsuranceCompany medicalInsuranceCompany) {
		try {
			MedicalInsuranceCompany test = medicalInsuranceCompanyService.findByMedicalInsuranceCompanyId(medicalInsuranceCompany.getMedicalInsuranceCompanyId());
			if (test == null) {
				throw new Exception("Medical Insurance Company not found");
			}

			medicalInsuranceCompany = medicalInsuranceCompanyService.save(medicalInsuranceCompany);
			return ResponseEntity.ok(medicalInsuranceCompany);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> newMedicalInsuranceCompany(@RequestBody MedicalInsuranceCompany medicalInsuranceCompany) {
		try {
			MedicalInsuranceCompany test = medicalInsuranceCompanyService.findByMedicalInsuranceCompanyId(medicalInsuranceCompany.getMedicalInsuranceCompanyId());
			if (test != null) {
				throw new Exception("The Medical Insurance Company already exists");
			}

			medicalInsuranceCompany = medicalInsuranceCompanyService.save(medicalInsuranceCompany);
			return ResponseEntity.ok(medicalInsuranceCompany);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteMedicalInsuranceCompany(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long id = mu.getAsLongOrNull(data, "medicalInsuranceCompanyId");
		String sql = "CALL ig_db.deleteMedicalInsuranceCompany(?);";

		try {	//**//					
			Object[] params = {		
				id	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/plan/delete")
	public ResponseEntity<?> deleteMedicalInsurancePlan(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long id = mu.getAsLongOrNull(data, "medicalInsurancePlanId");
		String sql = "CALL ig_db.deleteMedicalInsurancePlan(?);";

		try {	//**//					
			Object[] params = {		
				id	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/plan")
	public ResponseEntity<?> saveMedicalInsurancePlan(@RequestBody MedicalInsurancePlan medicalInsurancePlan) {
		try {
			MedicalInsurancePlan test = medicalInsuranceCompanyService.findMedicalInsurancePlan(medicalInsurancePlan.getMedicalInsurancePlanId());

			if (test == null) {
				throw new Exception("Medical Insurance Plan not found");
			}

			medicalInsurancePlan = medicalInsuranceCompanyService.save(medicalInsurancePlan);
			return ResponseEntity.ok(medicalInsurancePlan);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/plan/new")
	public ResponseEntity<?> newMedicalInsurancePlan(@RequestBody MedicalInsurancePlan medicalInsurancePlan) {
		try {
			MedicalInsurancePlan test = medicalInsuranceCompanyService.findMedicalInsurancePlan(medicalInsurancePlan.getMedicalInsurancePlanId());

			if (test != null) {
				throw new Exception("The Medical Insurance Plan already exists");
			}

			medicalInsurancePlan = medicalInsuranceCompanyService.save(medicalInsurancePlan);
			return ResponseEntity.ok(medicalInsurancePlan);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
