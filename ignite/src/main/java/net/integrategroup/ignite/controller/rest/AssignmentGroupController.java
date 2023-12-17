

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

import net.integrategroup.ignite.persistence.model.AssignmentGroup;
import net.integrategroup.ignite.persistence.model.Project;
import net.integrategroup.ignite.persistence.service.AssignmentGroupService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/assignment-group")
public class AssignmentGroupController {

	@Autowired
	public AssignmentGroupService assignmentGroupService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAssignmentGroups() {
		try {
			List<AssignmentGroup> result = assignmentGroupService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/for-individual/{individualId}")
	public ResponseEntity<?> getAssignmentGroups(ModelMap modelMap, @PathVariable(name = "individualId") Long individualId) {
		try {
			List<AssignmentGroup> result = assignmentGroupService.findAssignmentGroupsPerInd(individualId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/project-list/{individualId}")
	public ResponseEntity<?> getProjects(ModelMap modelMap, @PathVariable(name = "individualId") Long individualId) {
		try {
			List<Project> result = assignmentGroupService.findProjectsPerInd(individualId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping()
	public ResponseEntity<?> saveAssignmentGroup(@RequestBody AssignmentGroup assignmentGroup) {
		try {
			AssignmentGroup test = assignmentGroupService.findByAssignmentGroupId(assignmentGroup.getAssignmentGroupId());
			if (test == null) {
				throw new Exception("Assignment Group not found");
			}

			assignmentGroup = assignmentGroupService.save(assignmentGroup);
			return ResponseEntity.ok(assignmentGroup);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveAssignmentGroupNew(@RequestBody AssignmentGroup assignmentGroup) {
		try {

			AssignmentGroup test = assignmentGroupService.findByAssignmentGroupId(assignmentGroup.getAssignmentGroupId());
			if (test != null) {
				throw new Exception("The Assignment Group already exists");
			}

			assignmentGroup = assignmentGroupService.save(assignmentGroup);
			return ResponseEntity.ok(assignmentGroup);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteAssignmentGroup(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long assignmentGroupId = mu.getAsLongOrNull(data, "assignmentGroupId");
		String sql = "CALL ig_db.deleteAssignmentGroup(?);";

		System.out.println ("CALL ig_db.deleteAssignmentGroup(" + assignmentGroupId+");");
		try {
			Object[] params = {
					assignmentGroupId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
