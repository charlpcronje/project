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

import net.integrategroup.ignite.persistence.model.VehicleTyreAndRimType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.VehicleTyreAndRimTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/vehicle-tyre-and-rim-type")
public class VehicleTyreAndRimTypeController {

	@Autowired
	VehicleTyreAndRimTypeService vehicleTyreAndRimTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getVehicleTyreAndRimType() {
		try {
			List<VehicleTyreAndRimType> result = vehicleTyreAndRimTypeService.getVehicleTyreAndRimType();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveVehicleTyreAndRimType(@RequestBody VehicleTyreAndRimType vehicleTyreAndRimType) {
		try {

			VehicleTyreAndRimType test = vehicleTyreAndRimTypeService
					.findByVehicleTyreAndRimTypeId(vehicleTyreAndRimType.getVehicleTyreAndRimTypeId());
			if (test == null) {
				throw new Exception(" Type not found");
			}

			vehicleTyreAndRimType = vehicleTyreAndRimTypeService.save(vehicleTyreAndRimType);
			return ResponseEntity.ok(vehicleTyreAndRimType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveVehicleTyreAndRimTypeNew(@RequestBody VehicleTyreAndRimType vehicleTyreAndRimType) {
		try {

			VehicleTyreAndRimType test = vehicleTyreAndRimTypeService
					.findByVehicleTyreAndRimTypeId(vehicleTyreAndRimType.getVehicleTyreAndRimTypeId());
			if (test != null) {
				throw new Exception("VehicleTyreAndRimType already exists");
			}

			vehicleTyreAndRimType = vehicleTyreAndRimTypeService.save(vehicleTyreAndRimType);
			return ResponseEntity.ok(vehicleTyreAndRimType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteVehicleTyreAndRimType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long vehicleTyreAndRimTypeId = mu.getAsLongOrNull(data, "vehicleTyreAndRimTypeId");
		String sql = "CALL ig_db.deleteVehicleTyreAndRimType(?);";

		System.out.println ("CALL ig_db.deleteVehicleTyreAndRimType(" + vehicleTyreAndRimTypeId +");");
		try {	//**//					
			Object[] params = {		
					vehicleTyreAndRimTypeId	
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
