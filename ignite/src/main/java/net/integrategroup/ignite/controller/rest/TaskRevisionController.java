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

import net.integrategroup.ignite.persistence.model.TaskRevision;
import net.integrategroup.ignite.persistence.service.BranchService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.TaskRevisionService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/task-revision")
public class TaskRevisionController {

	@Autowired
	TaskRevisionService taskRevisionService;

	@Autowired
	BranchService branchService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllTaskRevisions() {
		try {
			List<TaskRevision> result = taskRevisionService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveTaskRevision(@RequestBody TaskRevision taskRevision) {
		try {

			TaskRevision test = taskRevisionService.findByTaskRevisionId(taskRevision.getTaskRevisionId());
			if (test == null) {
				throw new Exception("Task Revision not found");
			}

			taskRevision = taskRevisionService.save(taskRevision);
			return ResponseEntity.ok(taskRevision);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveTaskRevisionNew(@RequestBody TaskRevision taskRevision) {
		try {
			TaskRevision test = taskRevisionService.findByTaskRevisionId(taskRevision.getTaskRevisionId());
			if (test != null) {
				throw new Exception("The Task Revision already exists");
			}

			taskRevision = taskRevisionService.save(taskRevision);
			return ResponseEntity.ok(taskRevision);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteTaskRevision(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long taskRevisionId = mu.getAsLongOrNull(data, "taskRevisionId");
		String sql = "CALL ig_db.deleteTaskRevision(?);";

		System.out.println ("CALL ig_db.deleteTaskRevision(" + taskRevisionId +");");
		try {	//**//					
			Object[] params = {		
				taskRevisionId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list/{taskId}")
	public ResponseEntity<?> findAllTaskRevisionsForTask(@PathVariable("taskId") Long taskId, ModelMap modelMap) {
		try {
			List<TaskRevision> result = taskRevisionService.findAllTaskRevisionsForTask(taskId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/next-revision-number/{taskId}")
	public ResponseEntity<?> getNextRevisionNumber(@PathVariable("taskId") Long taskId, ModelMap modelMap) {
		try {

			Integer result = taskRevisionService.getNextRevisionNumber(taskId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
