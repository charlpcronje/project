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

import net.integrategroup.ignite.persistence.model.VehicleItem;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.VehicleItemService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/vehicle-item")
public class VehicleItemController {

	@Autowired
	VehicleItemService vehicleItemService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllVehicleItems() {
		try {
			List<VehicleItem> result = vehicleItemService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveVehicleItem(@RequestBody VehicleItem vehicleItem) {
		try {

			VehicleItem test = vehicleItemService.findByVehicleItemId(vehicleItem.getVehicleItemId());
			if (test == null) {
				throw new Exception("Vehicle not found");
			}

			vehicleItem = vehicleItemService.save(vehicleItem);
			return ResponseEntity.ok(vehicleItem);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveVehicleItemNew(@RequestBody VehicleItem vehicleItem) {
		try {
			VehicleItem test = vehicleItemService.findByVehicleItemId(vehicleItem.getVehicleItemId());
			if (test != null) {
				throw new Exception("The VehicleItem already exists");
			}

			vehicleItem = vehicleItemService.save(vehicleItem);
			return ResponseEntity.ok(vehicleItem);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteVehicleItem(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long vehicleItemId = mu.getAsLongOrNull(data, "vehicleItemId");
		String sql = "CALL ig_db.deleteVehicleItem(?);";

		System.out.println ("CALL ig_db.deleteVehicleItem(" + vehicleItemId +");");
		try {	//**//					
			Object[] params = {		
				vehicleItemId	
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
			List<VehicleItem> result = vehicleItemService.findByVehicleId(vehicleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}
