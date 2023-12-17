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

import net.integrategroup.ignite.persistence.model.Assignment;
import net.integrategroup.ignite.persistence.model.Task;
import net.integrategroup.ignite.persistence.model.VAssignmentForIndividualList;
import net.integrategroup.ignite.persistence.model.VAssignmentList;
import net.integrategroup.ignite.persistence.model.VAssignmentListNewSubs;
import net.integrategroup.ignite.persistence.model.VProjectParticipant;
import net.integrategroup.ignite.persistence.service.AssignmentService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ProjectParticipantService;
import net.integrategroup.ignite.persistence.service.ProjectService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/assignment")
public class AssignmentController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	AssignmentService assignmentService;

	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectParticipantService projectParticipantService;

	@Autowired
	SecurityUtils securityUtils;





	@GetMapping("/list/{firstDay}/{lastDay}")
	public ResponseEntity<?> getAllAssignments(	ModelMap modelMap,
												@PathVariable(name = "firstDay") Date firstDay,
												@PathVariable(name = "lastDay") Date lastDay) {
		try {
			List<VAssignmentList> result = assignmentService.getAllAssignments(firstDay, lastDay);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list-current/{firstDay}/{lastDay}")
	public ResponseEntity<?> getAllCurrentAssignments(ModelMap modelMap,
												@PathVariable(name = "firstDay") Date firstDay,
												@PathVariable(name = "lastDay") Date lastDay) {
		try {
			List<VAssignmentList> result = assignmentService.getAllCurrentAssignments(firstDay, lastDay);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list-current-new/{firstDay}/{lastDay}")
	public ResponseEntity<?> getAllCurrentAssignmentsWithNewSubs(ModelMap modelMap,
												@PathVariable(name = "firstDay") Date firstDay,
												@PathVariable(name = "lastDay") Date lastDay) {
		try {
			List<VAssignmentListNewSubs> result = assignmentService.getAllCurrentAssignmentsWithNewSubs(firstDay, lastDay);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/tasks/{assignmentId}")
	public ResponseEntity<?> findAllTasksForAssignment(@PathVariable("assignmentId") Long assignmentId, ModelMap modelMap) {
		try {
			List<Task> result = assignmentService.findAllTasksForAssignment(assignmentId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteAssignment(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long assignmentId = mu.getAsLongOrNull(data, "assignmentId");
		String sql = "CALL ig_db.deleteAssignment(?);";

		System.out.println ("CALL ig_db.deleteAssignment(" + assignmentId +");");
		try {
			Object[] params = {
					assignmentId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveAssignment(@RequestBody Assignment assignment) {
		try {

			Assignment test = assignmentService.findByAssignmentId(assignment.getAssignmentId());
			if (test == null) {
				throw new Exception("Assignment not found");
			}

			assignment = assignmentService.save(assignment);
			return ResponseEntity.ok(assignment);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveAssignmentNew(@RequestBody Assignment assignment) {
		try {
			Assignment test = assignmentService.findByAssignmentId(assignment.getAssignmentId());
			if (test != null) {
				throw new Exception("The Assignment ID already exists");
			}

			assignment = assignmentService.save(assignment);
			return ResponseEntity.ok(assignment);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project-participants/{projectId}")
	public ResponseEntity<?> findAllInPPViewForProjectId(@PathVariable("projectId") Long projectId, ModelMap modelMap) {
		try {
			List<VProjectParticipant> result = assignmentService.findAllInPPViewForProjectId(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@GetMapping("/project-participants-indiv/{projectId}")
	public ResponseEntity<?> findAllInPPViewForProjectIdIndiv(@PathVariable("projectId") Long projectId, ModelMap modelMap) {
		try {
			List<VProjectParticipant> result = assignmentService.findAllInPPViewForProjectIdIndiv(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/my-assignments/{individualId}")
	public ResponseEntity<?> findAllAssignmentsForIndividual(@PathVariable("individualId") Long individualId, ModelMap modelMap) {
		try {
			List<VAssignmentForIndividualList> result = assignmentService.findAllAssignmentsForIndividual(individualId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/my-assignments-current/{individualId}")
	public ResponseEntity<?> findAllAssignmentsForIndividualCurrent(@PathVariable("individualId") Long individualId, ModelMap modelMap) {
		try {
			List<VAssignmentForIndividualList> result = assignmentService.findAllAssignmentsForIndividualCurrent(individualId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/insert-tasks")   ///{assignmentTypeId}/{assignmentId}")
	public ResponseEntity<?> doInsertTasksFromTaskType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long assignmentTypeId = mu.getAsLongOrNull(data, "assignmentTypeId");
		Long assignmentId = mu.getAsLongOrNull(data, "assignmentId");
		String sql = "CALL ig_db.doInsertTasksFromTaskType(?, ?);";

		try {
			Object[] params = {
					assignmentTypeId,
					assignmentId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/next-assignment-number/{projectId}")
	public ResponseEntity<?> getNextSubmissionNumber(@PathVariable("projectId") Long projectId, ModelMap modelMap) {
		try {

			Integer result = assignmentService.getNextAssignmentNumber(projectId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



}
