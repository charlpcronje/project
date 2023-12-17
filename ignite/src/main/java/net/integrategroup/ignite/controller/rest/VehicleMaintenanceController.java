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

import net.integrategroup.ignite.persistence.model.VehicleMaintenance;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.VehicleMaintenanceService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/vehicle-maintenance")
public class VehicleMaintenanceController {

	@Autowired
	VehicleMaintenanceService vehicleMaintenanceService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllVehicleMaintenances() {
		try {
			List<VehicleMaintenance> result = vehicleMaintenanceService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveVehicleMaintenance(@RequestBody VehicleMaintenance vehicleMaintenance) {
		try {

			VehicleMaintenance test = vehicleMaintenanceService.findByVehicleMaintenanceId(vehicleMaintenance.getVehicleMaintenanceId());
			if (test == null) {
				throw new Exception("Vehicle not found");
			}

			vehicleMaintenance = vehicleMaintenanceService.save(vehicleMaintenance);
			return ResponseEntity.ok(vehicleMaintenance);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveVehicleMaintenanceNew(@RequestBody VehicleMaintenance vehicleMaintenance) {
		try {
			VehicleMaintenance test = vehicleMaintenanceService.findByVehicleMaintenanceId(vehicleMaintenance.getVehicleMaintenanceId());
			if (test != null) {
				throw new Exception("The VehicleMaintenance already exists");
			}

			vehicleMaintenance = vehicleMaintenanceService.save(vehicleMaintenance);
			return ResponseEntity.ok(vehicleMaintenance);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteVehicleMaintenance(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long vehicleMaintenanceId = mu.getAsLongOrNull(data, "vehicleMaintenanceId");
		String sql = "CALL ig_db.deleteVehicleMaintenance(?);";

		System.out.println ("CALL ig_db.deleteVehicleMaintenance(" + vehicleMaintenanceId +");");
		try {	//**//					
			Object[] params = {		
					vehicleMaintenanceId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/per-vehicle/{vehicleId}")
	public ResponseEntity<?> findByVehicleId(@PathVariable("vehicleId") Long vehicleId,
		ModelMap modelMap) {
		try {
			List<VehicleMaintenance> result = vehicleMaintenanceService.findByVehicleId(vehicleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}
