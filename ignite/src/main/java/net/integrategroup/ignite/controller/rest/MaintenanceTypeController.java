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

import net.integrategroup.ignite.persistence.model.MaintenanceType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.MaintenanceTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/maintenance-type")
public class MaintenanceTypeController {

	@Autowired
	MaintenanceTypeService maintenanceTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getMaintenanceType() {
		try {
			List<MaintenanceType> result = maintenanceTypeService.getMaintenanceType();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	//This End point is for updates
	@PostMapping()
	public ResponseEntity<?> saveMaintenanceType(@RequestBody MaintenanceType maintenanceType) {
		try {

			MaintenanceType test = maintenanceTypeService
					.findByMaintenanceTypeId(maintenanceType.getMaintenanceTypeId());
			if (test == null) {
				throw new Exception(" Type not found");
			}

			test.setName(maintenanceType.getName());
			test.setDescription(maintenanceType.getDescription());


			maintenanceType = maintenanceTypeService.save(test);
			return ResponseEntity.ok(maintenanceType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveMaintenanceTypeNew(@RequestBody MaintenanceType maintenanceType) {
		try {

			MaintenanceType test = maintenanceTypeService
					.findByMaintenanceTypeId(maintenanceType.getMaintenanceTypeId());
			if (test != null) {
				throw new Exception("MaintenanceType already exists");
			}

			maintenanceType = maintenanceTypeService.save(maintenanceType);
			return ResponseEntity.ok(maintenanceType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteMaintenanceType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long maintenanceTypeId = mu.getAsLongOrNull(data, "maintenanceTypeId");
		String sql = "CALL ig_db.deleteMaintenanceType(?);";

		System.out.println ("CALL ig_db.deleteMaintenanceType(" + maintenanceTypeId +");");
		try {	//**//					
			Object[] params = {		
				maintenanceTypeId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@GetMapping("/not-linked/{vehicleId}")
//	public ResponseEntity<?> findMaintenanceTypeNotLinked(@PathVariable("vehicleId") Long vehicleId, ModelMap modelMap) {
//		try {
//			List<MaintenanceType> result = maintenanceTypeService.findMaintenanceTypeNotLinked(vehicleId);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}





}
