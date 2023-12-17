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

import net.integrategroup.ignite.persistence.model.VehicleReading;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.VehicleReadingService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/vehicle-reading")
public class VehicleReadingController {

	@Autowired
	VehicleReadingService vehicleReadingService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllVehicleReadings() {
		try {
			List<VehicleReading> result = vehicleReadingService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveVehicleReading(@RequestBody VehicleReading vehicleReading) {
		try {

			VehicleReading test = vehicleReadingService.findByVehicleReadingId(vehicleReading.getVehicleReadingId());
			if (test == null) {
				throw new Exception("Vehicle not found");
			}

			vehicleReading = vehicleReadingService.save(vehicleReading);
			return ResponseEntity.ok(vehicleReading);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveVehicleReadingNew(@RequestBody VehicleReading vehicleReading) {
		try {
			VehicleReading test = vehicleReadingService.findByVehicleReadingId(vehicleReading.getVehicleReadingId());
			if (test != null) {
				throw new Exception("The VehicleReading already exists");
			}

			vehicleReading = vehicleReadingService.save(vehicleReading);
			return ResponseEntity.ok(vehicleReading);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteVehicleReading(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long vehicleReadingId = mu.getAsLongOrNull(data, "vehicleReadingId");
		String sql = "CALL ig_db.deleteVehicleReading(?);";

		System.out.println ("CALL ig_db.deleteVehicleReading(" + vehicleReadingId +");");
		try {	//**//					
			Object[] params = {		
				vehicleReadingId	
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
			List<VehicleReading> result = vehicleReadingService.findByVehicleId(vehicleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}
