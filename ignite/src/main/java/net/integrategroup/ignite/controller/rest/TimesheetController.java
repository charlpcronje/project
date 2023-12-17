package net.integrategroup.ignite.controller.rest;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import net.integrategroup.ignite.persistence.model.TimesheetSummaryDto;
import net.integrategroup.ignite.persistence.model.TopDescriptionsUsed;
import net.integrategroup.ignite.persistence.model.VTimesheet;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.RemunerationRateUplineService;
import net.integrategroup.ignite.persistence.service.TimesheetService;
import net.integrategroup.ignite.persistence.service.TopDescriptionsUsedService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/timesheet")
public class TimesheetController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	TimesheetService timesheetService;

	@Autowired
	RemunerationRateUplineService remunerationRateUplineService;

	@Autowired
	TopDescriptionsUsedService topDescriptionsUsedService;


	@Autowired
	SecurityUtils securityUtils;

	@GetMapping("/list/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> getParticipantTimesheetList(	ModelMap modelMap,
															@PathVariable(name = "participantId") Long participantId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay") Long lastDay)
	{
		Date fd = new Date(firstDay); // Dates to and db
		Date ld = new Date(lastDay);

		try {
			List<VTimesheet> result = timesheetService.getParticipantTimesheetList(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/summary/{participantId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> getParticipantTimesheetSummary(	ModelMap modelMap,
															@PathVariable(name = "participantId") Long participantId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay") Long lastDay)

	{
		Date fd = new Date(firstDay); // Dates to and db
		Date ld = new Date(lastDay);

		try {
			List<TimesheetSummaryDto> result = timesheetService.getParticipantTimesheetSummary(participantId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@PostMapping()
//	public ResponseEntity<?> saveTimesheet(@RequestBody Timesheet timesheet) {
//		try {
//
//			Timesheet test = timesheetService.findByTimesheetId(timesheet.getTimesheetId());
//			if (test == null) {
//				throw new Exception("Timesheet Entry not found");
//			}
//
//			// Get the UnitTypeCode
////			RemunerationRateUpline remunerationRateUpline = remunerationRateUplineService.findByRemunerationRateUplineId(timesheet.getRemunerationRateUplineId());
////			if (remunerationRateUpline != null) {
////				timesheet.setUnitTypeCode(remunerationRateUpline.getUnitTypeCode());
////			}
//
//			timesheet = timesheetService.save(timesheet);
//			return ResponseEntity.ok(timesheet);
//		} catch (Exception e) {
//			 return ResponseEntity.badRequest().body(e.getMessage());
////			 return ResponseEntity.badRequest().body("Service Discipline, Remuneration Type and Activity Date must be unique");
//		}
//	}

//	@PostMapping("/new")
//	public ResponseEntity<?> saveTimesheetNew(@RequestBody Timesheet timesheet) {
//		try {
//
//			Timesheet test = timesheetService.findByTimesheetId(timesheet.getTimesheetId());
//			if (test != null) {
//				throw new Exception("Timesheet Entry already exists");
//			}
//
//			timesheet = timesheetService.save(timesheet);
//			return ResponseEntity.ok(timesheet);
//		} catch (Exception e) {
//			 return ResponseEntity.badRequest().body(e.getMessage());
////			 return ResponseEntity.badRequest().body("Service Discipline, Remuneration Type and Activity Date must be unique");
//		}
//	}

	@PostMapping()
	public ResponseEntity<?> saveTimesheet(@RequestBody Map<String, Object> data) {
		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveTimesheet('" + jsonString + "', '"
					+ securityUtils.getUsername() + "');";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

			String sql = "CALL ig_db.saveTimesheet(?, ?);";

			Object[] params = {
					jsonString, 
					securityUtils.getUsername()
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveTimesheetNew(@RequestBody Map<String, Object> data) {
		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);
			
			timesheetService.saveTimesheetNew(jsonString);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();  // Do this when calling a sp!
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/delete")
	public ResponseEntity<?> deleteTimesheet(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long timesheetId = mu.getAsLongOrNull(data, "timesheetId");
		String sql = "CALL ig_db.deleteTimesheet(?);";

		System.out.println ("CALL ig_db.deleteTimesheet(" + timesheetId + ");");
		try {
			Object[] params = {timesheetId};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/per-project/summary/{projectId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> getProjectTimesheetSummary(	ModelMap modelMap,
															@PathVariable(name = "projectId") Long projectId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay") Long lastDay)

	{
		Date fd = new Date(firstDay); // Dates to and db
		Date ld = new Date(lastDay);

		try {
			List<TimesheetSummaryDto> result = timesheetService.getProjectTimesheetSummary(projectId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@GetMapping("/project-list/{projectId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> getProjectTimesheetList(	ModelMap modelMap,
															@PathVariable(name = "projectId") Long projectId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay") Long lastDay)

	{
		Date fd = new Date(firstDay); // Dates to and db
		Date ld = new Date(lastDay);

		try {
			List<VTimesheet> result = timesheetService.getProjectTimesheetList(projectId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@GetMapping("/last-ten-descriptions/{projectParticipantId}")
	public ResponseEntity<?> findByProjectParticipantId(	ModelMap modelMap,
															@PathVariable(name = "projectParticipantId") Long projectParticipantId)
	{
		try {
			List<TopDescriptionsUsed> result = topDescriptionsUsedService.findByProjectParticipantId(projectParticipantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/list-per-proj/{participantId}/{sdId}/{projectId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> getPerProjectSdTimesheetList(	ModelMap modelMap,
															@PathVariable(name = "participantId") Long participantId,
															@PathVariable(name = "sdId") Long sdId,
															@PathVariable(name = "projectId") Long projectId,
															@PathVariable(name = "firstDay") Long firstDay,
															@PathVariable(name = "lastDay") Long lastDay)
 	{
		Date fd = new Date(firstDay); // Dates to and db
		Date ld = new Date(lastDay);

		try {
			List<VTimesheet> result = timesheetService.getPerProjectSdTimesheetList(participantId, sdId, projectId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
