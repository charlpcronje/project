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

import net.integrategroup.ignite.persistence.model.DeliverableType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.DeliverableTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/deliverable-type")
public class DeliverableTypeController {
	@Autowired
	DeliverableTypeService deliverableTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> findAll() {
		try {
			List<DeliverableType> result = deliverableTypeService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/serviceDisciplineIdIndustry/{serviceDisciplineIdIndustry}")
	public ResponseEntity<?> findByServiceDisciplineIdIndustry(
			@PathVariable("serviceDisciplineIdIndustry") Long serviceDisciplineIdIndustry, ModelMap modelMap) {
		try {
			List<DeliverableType>  result = deliverableTypeService.findByServiceDisciplineIdIndustry(serviceDisciplineIdIndustry);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/{deliverableTypeId}")
	public ResponseEntity<?> findByDeliverableTypeId(
			@PathVariable("deliverableTypeId") Long deliverableTypeId, ModelMap modelMap) {
		try {
			DeliverableType result = deliverableTypeService.findByDeliverableTypeId(deliverableTypeId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveDeliverableType(@RequestBody DeliverableType deliverableType) {
		try {
			DeliverableType test = deliverableTypeService.findByDeliverableTypeId(deliverableType.getDeliverableTypeId());
			if (test == null) {
				throw new Exception("Expense Type not found");
			}

			deliverableType = deliverableTypeService.save(deliverableType);
			return ResponseEntity.ok(deliverableType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveDeliverableTypeNew(@RequestBody DeliverableType deliverableType) {
		try {
			DeliverableType test = deliverableTypeService.findByDeliverableTypeId(deliverableType.getDeliverableTypeId());
			if (test != null) {
				throw new Exception("The Expense Type already exists");
			}

			deliverableType = deliverableTypeService.save(deliverableType);
			return ResponseEntity.ok(deliverableType);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteDeliverableType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long deliverableTypeId = mu.getAsLongOrNull(data, "deliverableTypeId");
		String sql = "CALL ig_db.deleteDeliverableType(?);";

		System.out.println ("CALL ig_db.deleteDeliverableType(" + deliverableTypeId +");");
		try {
			Object[] params = {
					deliverableTypeId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
