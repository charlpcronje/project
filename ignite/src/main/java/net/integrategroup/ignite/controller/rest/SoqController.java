package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.Soq;
import net.integrategroup.ignite.persistence.model.SoqSubSchedule;
import net.integrategroup.ignite.persistence.model.TripLoggerDto;
import net.integrategroup.ignite.persistence.model.TripLoggerPointNameDto;
import net.integrategroup.ignite.persistence.model.VTripLogger;
import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.SoqService;
import net.integrategroup.ignite.persistence.service.TripLoggerService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/soq-utility")
public class SoqController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SoqService soqService;

	@GetMapping("/all")
	public ResponseEntity<?> viewSoq(ModelMap modelMap) {
		try {
			List<Soq> result = soqService.viewSoq();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/all-sub-schedules")
	public ResponseEntity<?> viewSoqSubSchedule(ModelMap modelMap) {
		try {
			List<SoqSubSchedule> result = soqService.viewSoqSubSchedule();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping("/new")
	public ResponseEntity<?> saveSoqNew(@RequestBody Soq soq) {
		try {
			Soq test = soqService.findBySoqCode(soq.getSoqModeCode());
			if (test != null) {
				throw new Exception("The Logged Trip already exists");
			}

			soq = soqService.save(soq);
			return ResponseEntity.ok(soq);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveSoq(@RequestBody Soq soq) {
		try {

			Soq test = soqService.findBySoqCode(soq.getSoqModeCode());
			if (test == null) {
				throw new Exception("Logged Trip not found");
			}

			soq = soqService.save(soq);
			return ResponseEntity.ok(soq);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	 @PutMapping("/update")
	    public ResponseEntity<?> updateSoq(@RequestBody Soq soq) {
	   
	        try {
	        	soqService.save(soq);
	            return ResponseEntity.ok("Data updated successfully");
	        } catch (Exception e) {
	        	return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }


	@PostMapping("/delete")
	public ResponseEntity<?> deleteTripLogger(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long tripLoggerId = mu.getAsLongOrNull(data, "tripLoggerId");
		String sql = "CALL ig_db.deleteTripLogger(?);";

		System.out.println ("CALL ig_db.deleteTripLogger(" + tripLoggerId+");");
		try {	//**//					
			Object[] params = {		
				tripLoggerId	
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
