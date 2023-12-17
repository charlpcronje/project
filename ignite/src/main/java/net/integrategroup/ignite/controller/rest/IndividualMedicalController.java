package net.integrategroup.ignite.controller.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.MainMemberMedicalInsurance;
import net.integrategroup.ignite.persistence.model.MedicalDependant;
import net.integrategroup.ignite.persistence.service.IndividualMedicalService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/individual-medical")
public class IndividualMedicalController {

	@Autowired
	IndividualMedicalService individualMedicalService;

	@GetMapping("{individualId}")
	public ResponseEntity<?> getIndividualMedicalInformation(@PathVariable("individualId") Long individualId) {
		try {
			MainMemberMedicalInsurance result = individualMedicalService.getMainMemberMedical(individualId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/dependants/{individualId}")
	public ResponseEntity<?> getIndividualDependants(@PathVariable("individualId") Long individualId) {
		try {
			List<MedicalDependant> result = individualMedicalService.getDependants(individualId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveMainMemberMedicalInsurance(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			Long individualId = mu.getAsLongOrNull(data, "individualId");
			Long medicalInsuranceCompanyId = mu.getAsLongOrNull(data, "medicalInsuranceCompanyId");
			Long medicalInsurancePlanId = mu.getAsLongOrNull(data, "medicalInsurancePlanId");
			String insuranceNumber = mu.getAsStringOrNull(data, "insuranceNumber");
			String description = mu.getAsStringOrNull(data, "description");

			individualMedicalService.saveMainMemberMedicalInsurance(individualId,
					medicalInsuranceCompanyId, medicalInsurancePlanId, insuranceNumber, description);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("dependant/delete")
	public ResponseEntity<?> deleteDependant(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();
			Long medicalDependantId = mu.getAsLongOrNull(data, "medicalDependantId");

			individualMedicalService.deleteDependant(medicalDependantId);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("dependant/save")
	public ResponseEntity<?> saveDependant(@RequestBody MedicalDependant medicalDependant){ //HashMap<String, Object> data) {
		try {
			/*
			MapUtils mu = new MapUtils();
			Long medicalDependantId = mu.getAsLongOrNull(data, "medicalDependantId");
			Long mainMemberMedicalInsuranceId = mu.getAsLongOrNull(data, "mainMemberMedicalInsuranceId");
			Long individualIdDependant = mu.getAsLongOrNull(data, "individualIdDependant");
			String description = mu.getAsStringOrNull(data, "description");
			*/

			Long medicalDependantId = medicalDependant.getMedicalDependantId();
			Long mainMemberMedicalInsuranceId = medicalDependant.getMainMemberMedicalInsuranceId();
			Long individualIdDependant = medicalDependant.getIndividualIdDependant();
			String description = medicalDependant.getDescription();

			individualMedicalService.saveDependant(
					medicalDependantId,
					mainMemberMedicalInsuranceId,
					individualIdDependant,
					description);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
