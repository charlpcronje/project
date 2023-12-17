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

import net.integrategroup.ignite.persistence.model.TaskSubmission;
import net.integrategroup.ignite.persistence.service.BranchService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.TaskSubmissionService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/task-submission")
public class TaskSubmissionController {

	@Autowired
	TaskSubmissionService taskSubmissionService;

	@Autowired
	BranchService branchService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllTaskSubmissions() {
		try {
			List<TaskSubmission> result = taskSubmissionService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveTaskSubmission(@RequestBody TaskSubmission taskSubmission) {
		try {

			TaskSubmission test = taskSubmissionService.findByTaskSubmissionId(taskSubmission.getTaskSubmissionId());
			if (test == null) {
				throw new Exception("Task Submission not found");
			}

			taskSubmission = taskSubmissionService.save(taskSubmission);
			return ResponseEntity.ok(taskSubmission);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveTaskSubmissionNew(@RequestBody TaskSubmission taskSubmission) {
		try {
			TaskSubmission test = taskSubmissionService.findByTaskSubmissionId(taskSubmission.getTaskSubmissionId());
			if (test != null) {
				throw new Exception("The Task Submission already exists");
			}

			taskSubmission = taskSubmissionService.save(taskSubmission);
			return ResponseEntity.ok(taskSubmission);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteTaskSubmission(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long taskSubmissionId = mu.getAsLongOrNull(data, "taskSubmissionId");
		String sql = "CALL ig_db.deleteTaskSubmission(?);";

		System.out.println ("CALL ig_db.deleteTaskSubmission(" + taskSubmissionId +");");
		try {	//**//					
			Object[] params = {		
				taskSubmissionId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list/{taskRevisionId}")
	public ResponseEntity<?> findAllTaskSubmissionsForTaskRevision(@PathVariable("taskRevisionId") Long taskRevisionId, ModelMap modelMap) {
		try {
			List<TaskSubmission> result = taskSubmissionService.findAllTaskSubmissionsForTaskRevision(taskRevisionId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/next-submission-number/{taskRevisionId}")
	public ResponseEntity<?> getNextSubmissionNumber(@PathVariable("taskRevisionId") Long taskRevisionId, ModelMap modelMap) {
		try {

			Integer result = taskSubmissionService.getNextSubmissionNumber(taskRevisionId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping("/set-task-complete")
	public ResponseEntity<?> doMarkTaskAsComplete(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long taskId = mu.getAsLongOrNull(data, "taskId");
		String sql = "CALL ig_db.doMarkTaskAsComplete(?);";

		System.out.println ("CALL ig_db.doMarkTaskAsComplete(" + taskId +");");

		try {	//**//					
			Object[] params = {		
				taskId	
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
