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

import net.integrategroup.ignite.persistence.model.TaskType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.TaskTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/task-type")
public class TaskTypeController {

	@Autowired
	TaskTypeService taskTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getTaskType() {
		try {
			List<TaskType> result = taskTypeService.getTaskType();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/by-parent/{assignmentTypeId}")
	public ResponseEntity<?> findByAssignmentTypeId(
			@PathVariable("assignmentTypeId") Long assignmentTypeId, ModelMap modelMap) {
		try {
			List<TaskType> result = taskTypeService.findByAssignmentTypeId(assignmentTypeId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping()
	public ResponseEntity<?> saveTaskType(@RequestBody TaskType taskType) {
		try {

			TaskType test = taskTypeService
					.findByTaskTypeId(taskType.getTaskTypeId());
			if (test == null) {
				throw new Exception("Task Type not found");
			}

			taskType = taskTypeService.save(taskType);
			return ResponseEntity.ok(taskType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveTaskTypeNew(@RequestBody TaskType taskType) {
		try {

			TaskType test = taskTypeService
					.findByTaskTypeId(taskType.getTaskTypeId());
			if (test != null) {
				throw new Exception("Task Type already exists");
			}

			taskType = taskTypeService.save(taskType);
			return ResponseEntity.ok(taskType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/delete")
	public ResponseEntity<?> deleteTaskType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long taskTypeId = mu.getAsLongOrNull(data, "taskTypeId");
		String sql = "CALL ig_db.deleteTaskType(?);";

		System.out.println ("CALL ig_db.deleteTaskType(" + taskTypeId +");");
		try {	//**//					
			Object[] params = {		
				taskTypeId	
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
