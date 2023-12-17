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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.TripLogger;
import net.integrategroup.ignite.persistence.model.TripLoggerDto;
import net.integrategroup.ignite.persistence.model.TripLoggerPointNameDto;
import net.integrategroup.ignite.persistence.model.VTripLogger;
import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.TripLoggerService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/trip-logger")
public class TripLoggerController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	TripLoggerService tripLoggerService;

	@GetMapping("/all-in-view")
	public ResponseEntity<?> viewTripLogger(ModelMap modelMap) {
		try {
			List<VTripLogger> result = tripLoggerService.viewTripLogger();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/vehicle")
	public ResponseEntity<?> viewVehicle(ModelMap modelMap) {
		try {
			List<VVehicle> result = tripLoggerService.viewVehicle();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/driver/{participantId}/{vehicleId}")
	public ResponseEntity<?> getVehicleByDriver(@PathVariable("participantId") Long participantId,@PathVariable("vehicleId") Long vehicleId , ModelMap modelMap) {
		try {
			List<Vehicle> result = tripLoggerService.getVehicleByDriver(participantId, vehicleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/startPoint/{projectId}")
	public ResponseEntity<?> getStartPointByProject(@PathVariable("projectId") Long projectId, ModelMap modelMap) {
		try {
			List<TripLoggerPointNameDto> result = tripLoggerService.getStartPointByProject(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/endPoint/{projectId}")
	public ResponseEntity<?> getEndPointByProject(@PathVariable("projectId") Long projectId, ModelMap modelMap) {
		try {
			List<TripLoggerPointNameDto> result = tripLoggerService.getEndPointByProject(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/instructedBy/{participantId}/{participantIdOnBehalfOf}")
	public ResponseEntity<?> getBehalfOfById(@PathVariable("participantId") Long participantId, @PathVariable("participantIdOnBehalfOf") Long participantIdOnBehalfOf, ModelMap modelMap) {
	    try {
	        List<TripLoggerDto> result = tripLoggerService.getBehalfOfById(participantId, participantIdOnBehalfOf);
	        return ResponseEntity.ok(result);
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}



	@GetMapping("/list/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> getParticipantTimesheetList(	ModelMap modelMap,
															@PathVariable(name = "tripLoggerId") Long tripLoggerId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay") Long lastDay)
	{
		Date fd = new Date(firstDay); // Dates to and db
		Date ld = new Date(lastDay);

		try {
			List<VTripLogger> result = tripLoggerService.getTripLoggerList(tripLoggerId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping("/new")
	public ResponseEntity<?> saveTripLoggerNew(@RequestBody TripLogger tripLogger) {
		try {
			TripLogger test = tripLoggerService.findByTripLoggerId(tripLogger.getTripLoggerId());
			if (test != null) {
				throw new Exception("The Logged Trip already exists");
			}

			tripLogger = tripLoggerService.save(tripLogger);
			return ResponseEntity.ok(tripLogger);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveTripLogger(@RequestBody TripLogger tripLogger) {
		try {

			TripLogger test = tripLoggerService.findByTripLoggerId(tripLogger.getTripLoggerId());
			if (test == null) {
				throw new Exception("Logged Trip not found");
			}

			tripLogger = tripLoggerService.save(tripLogger);
			return ResponseEntity.ok(tripLogger);
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
