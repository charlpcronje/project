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

import net.integrategroup.ignite.persistence.model.Task;
import net.integrategroup.ignite.persistence.service.BranchService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.TaskService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/task")
public class TaskController {

	@Autowired
	TaskService taskService;

	@Autowired
	BranchService branchService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllTasks() {
		try {
			List<Task> result = taskService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveTask(@RequestBody Task task) {
		try {

			Task test = taskService.findByTaskId(task.getTaskId());
			if (test == null) {
				throw new Exception("Task not found");
			}

			task = taskService.save(task);
			return ResponseEntity.ok(task);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveTaskNew(@RequestBody Task task) {
		try {
			Task test = taskService.findByTaskId(task.getTaskId());
			if (test != null) {
				throw new Exception("The Task already exists");
			}

			task = taskService.save(task);
			return ResponseEntity.ok(task);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteTask(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long taskId = mu.getAsLongOrNull(data, "taskId");
		String sql = "CALL ig_db.deleteTask(?);";

		System.out.println ("CALL ig_db.deleteTask(" + taskId +");");
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

	@GetMapping("/max-revision/{taskId}")
	public ResponseEntity<?> getMaxRevisionNumber(@PathVariable("taskId") Long taskId, ModelMap modelMap) {
		try {

			Integer result = taskService.getMaxRevisionNumber(taskId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
