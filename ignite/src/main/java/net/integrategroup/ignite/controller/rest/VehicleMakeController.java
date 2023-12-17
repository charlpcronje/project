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

import net.integrategroup.ignite.persistence.model.VehicleMake;
import net.integrategroup.ignite.persistence.model.VehicleModel;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.VehicleMakeService;
import net.integrategroup.ignite.persistence.service.VehicleModelService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/vehicle-make")
public class VehicleMakeController {

	@Autowired
	VehicleMakeService vehicleMakeService;

	@Autowired
	VehicleModelService vehicleModelService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllVehicleMakes() {
		try {
			List<VehicleMake> result = vehicleMakeService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveVehicleMake(@RequestBody VehicleMake vehicleMake) {
		try {

			VehicleMake test = vehicleMakeService.findByVehicleMakeId(vehicleMake.getVehicleMakeId());
			if (test == null) {
				throw new Exception("VehicleMake not found");
			}

			vehicleMake = vehicleMakeService.save(vehicleMake);
			return ResponseEntity.ok(vehicleMake);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveVehicleMakeNew(@RequestBody VehicleMake vehicleMake) {
		try {
			VehicleMake test = vehicleMakeService.findByVehicleMakeId(vehicleMake.getVehicleMakeId());
			if (test != null) {
				throw new Exception("The Vehicle Make already exists");
			}

			vehicleMake = vehicleMakeService.save(vehicleMake);
			return ResponseEntity.ok(vehicleMake);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteVehicleMake(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long vehicleMakeId = mu.getAsLongOrNull(data, "vehicleMakeId");
		String sql = "CALL ig_db.deleteVehicleMake(?);";

		System.out.println ("CALL ig_db.deleteVehicleMake(" + vehicleMakeId +");");
		try {	//**//					
			Object[] params = {		
				vehicleMakeId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}







	@GetMapping("/vehicle-model/{vehicleMakeId}")
	public ResponseEntity<?> findByVehicleMakeId(@PathVariable("vehicleMakeId") Long vehicleMakeId,
			ModelMap modelMap) {
		try {
			List<VehicleModel> result = vehicleModelService.findByVehicleMakeId(vehicleMakeId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping("/vehicle-model")
	public ResponseEntity<?> saveVehicleModel(@RequestBody VehicleModel vehicleModel) {
		try {

			VehicleModel test = vehicleModelService.findByVehicleModelId(vehicleModel.getVehicleModelId());
			if (test == null) {
				throw new Exception("VehicleModel not found");
			}
			vehicleModel = vehicleModelService.save(vehicleModel);
			return ResponseEntity.ok(vehicleModel);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/vehicle-model/new")
	public ResponseEntity<?> saveVehicleModelNew(@RequestBody VehicleModel vehicleModel) {
		try {

			VehicleModel test = vehicleModelService.findByVehicleModelId(vehicleModel.getVehicleModelId());
			if (test != null) {
				throw new Exception("The VehicleModel Id already exists");
			}
			vehicleModel = vehicleModelService.save(vehicleModel);
			return ResponseEntity.ok(vehicleModel);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("vehicle-model/delete")
	public ResponseEntity<?> deleteVehicleModel(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long vehicleModelId = mu.getAsLongOrNull(data, "vehicleModelId");
		String sql = "CALL ig_db.deleteVehicleModel(?);";

		System.out.println ("CALL ig_db.deleteVehicleModel(" + vehicleModelId + ");");
		try {	//**//					
			Object[] params = {		
				vehicleModelId	
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
