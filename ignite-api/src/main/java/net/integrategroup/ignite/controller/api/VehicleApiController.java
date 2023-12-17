package net.integrategroup.ignite.controller.api;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.Vehicle;
import net.integrategroup.ignite.persistence.service.IndividualService;
import net.integrategroup.ignite.persistence.service.VehicleService;
import net.integrategroup.ignite.utils.HttpUtils;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/vehicle")
public class VehicleApiController {
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	IndividualService individualService;
	
	@GetMapping
	public ResponseEntity<?> getVehicles(HttpServletRequest request) {
		try {			
			String username = HttpUtils.getHeaderVariable(request, ApiUtils.PARAM_IDENTIFIER + "u");
			
			// get vehicles associated with this users
			List<Vehicle> vehicleList = vehicleService.findVehicleByUsername(username);
			
			return ResponseEntity.ok(vehicleList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping(path = "")
	public ResponseEntity<?> saveNewVehicle(@RequestBody Map<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();
			
			// Get the fields out of mu(
			String username = mu.getAsStringOrNull(data, "username");
			/*
			    data.put("vehicleName", vehicleName);
		        data.put("vehicleMake", vehicleMake);
		        data.put("vehicleModel", vehicleModel);
		        data.put("licenseNumber", licenseNumber);
		        data.put("registrationYear", registrationYear);
		        data.put("vinNumber", vinNumber);
		        data.put("engineNumber", engineNumber);
		        data.put("username", getCargoValue("username"));
			 */
			// TODO: build a vehicle object and save it
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}	
}
