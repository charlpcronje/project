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

import net.integrategroup.ignite.persistence.model.AssignmentType;
import net.integrategroup.ignite.persistence.service.AssignmentTypeService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/assignment-type")
public class AssignmentTypeController {

	@Autowired
	AssignmentTypeService assignmentTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAssignmentType() {
		try {
			List<AssignmentType> result = assignmentTypeService.getAssignmentType();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveAssignmentType(@RequestBody AssignmentType assignmentType) {
		try {

			AssignmentType test = assignmentTypeService
					.findByAssignmentTypeId(assignmentType.getAssignmentTypeId());
			if (test == null) {
				throw new Exception("Assignment Type not found");
			}

			assignmentType = assignmentTypeService.save(assignmentType);
			return ResponseEntity.ok(assignmentType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveAssignmentTypeNew(@RequestBody AssignmentType assignmentType) {
		try {

			AssignmentType test = assignmentTypeService
					.findByAssignmentTypeId(assignmentType.getAssignmentTypeId());
			if (test != null) {
				throw new Exception("Assignment Type already exists");
			}

			assignmentType = assignmentTypeService.save(assignmentType);
			return ResponseEntity.ok(assignmentType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteAssignmentType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long assignmentTypeId = mu.getAsLongOrNull(data, "assignmentTypeId");
		String sql = "CALL ig_db.deleteAssignmentType(?);";

		System.out.println ("CALL ig_db.deleteAssignmentType(" + assignmentTypeId +");");
		try {
			Object[] params = {
					assignmentTypeId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}
