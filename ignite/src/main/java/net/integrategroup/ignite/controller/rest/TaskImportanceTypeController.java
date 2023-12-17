package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.TaskImportanceType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.TaskImportanceTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/task-importance-type")
public class TaskImportanceTypeController {

	@Autowired
	TaskImportanceTypeService taskImportanceTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getTaskImportanceType() {
		try {
			List<TaskImportanceType> result = taskImportanceTypeService.getTaskImportanceType();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveTaskImportanceType(@RequestBody TaskImportanceType taskImportanceType) {
		try {

			TaskImportanceType test = taskImportanceTypeService
					.findByTaskImportanceTypeId(taskImportanceType.getTaskImportanceTypeId());
			if (test == null) {
				throw new Exception("Task Importance Type not found");
			}

			taskImportanceType = taskImportanceTypeService.save(taskImportanceType);
			return ResponseEntity.ok(taskImportanceType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveTaskImportanceTypeNew(@RequestBody TaskImportanceType taskImportanceType) {
		try {

			TaskImportanceType test = taskImportanceTypeService
					.findByTaskImportanceTypeId(taskImportanceType.getTaskImportanceTypeId());
			if (test != null) {
				throw new Exception("Assignment Type already exists");
			}

			taskImportanceType = taskImportanceTypeService.save(taskImportanceType);
			return ResponseEntity.ok(taskImportanceType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteTaskImportanceType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long taskImportanceTypeId = mu.getAsLongOrNull(data, "taskImportanceTypeId");
		String sql = "CALL ig_db.deleteTaskImportanceType(?);";

		System.out.println ("CALL ig_db.deleteTaskImportanceType(" + taskImportanceTypeId +");");
		try {	//**//					
			Object[] params = {		
				taskImportanceTypeId	
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
