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

import net.integrategroup.ignite.persistence.model.ProjectStageType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ProjectStageTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/project-stage-type")
public class ProjectStageTypeController {

	@Autowired
	public ProjectStageTypeService projectStageTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> findAll() {
		try {
			List<ProjectStageType> result = projectStageTypeService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveProjectStageType(@RequestBody ProjectStageType projectStageType) {
		try {

			ProjectStageType test = projectStageTypeService
					.findByProjectStageTypeId(projectStageType.getProjectStageTypeId());
			if (test == null) {
				throw new Exception("Project Stage Type not found");
			}

			projectStageType = projectStageTypeService.save(projectStageType);
			return ResponseEntity.ok(projectStageType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveProjectStageTypeNew(@RequestBody ProjectStageType projectStageType) {
		try {

			ProjectStageType test = projectStageTypeService
					.findByProjectStageTypeId(projectStageType.getProjectStageTypeId());
			if (test != null) {
				throw new Exception("The Project Stage Type already exists");
			}

			projectStageType = projectStageTypeService.save(projectStageType);
			return ResponseEntity.ok(projectStageType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteProjectStageType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long projectStageTypeId = mu.getAsLongOrNull(data, "projectStageTypeId");
		String sql = "CALL ig_db.deleteProjectStageType(?);";

		System.out.println ("CALL ig_db.deleteProjectStageType(" + projectStageTypeId +");");
		try {	//**//					
			Object[] params = {		
				projectStageTypeId	
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
