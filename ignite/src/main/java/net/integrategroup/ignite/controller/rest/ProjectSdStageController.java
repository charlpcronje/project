package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.ProjectSdStage;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ProjectSdStageService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/project-sd-stage")
public class ProjectSdStageController {

	@Autowired
	ProjectSdStageService projectSdStageService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllProjectSdStages() {
		try {
			List<ProjectSdStage> result = projectSdStageService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/by-project-sd/{projectSdId}")
	public ResponseEntity<?> findProjectSdStageByProjectSd(@PathVariable("projectSdId") Long projectSdId) {
		try {
			List<ProjectSdStage> result = projectSdStageService.findProjectSdStageByProjectSd(projectSdId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}




	@PostMapping()
	public ResponseEntity<?> saveProjectSdStage(@RequestBody ProjectSdStage projectSdStage) {
		try {

			ProjectSdStage test = projectSdStageService.findByProjectSdStageId(projectSdStage.getProjectSdStageId());
			if (test == null) {
				throw new Exception("Project SD Stage not found");
			}

			projectSdStage = projectSdStageService.save(projectSdStage);
			return ResponseEntity.ok(projectSdStage);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/new")
	public ResponseEntity<?> saveProjectSdStageNew(@RequestBody ProjectSdStage projectSdStage) {
		try {

			ProjectSdStage test = projectSdStageService.findByProjectSdStageId(projectSdStage.getProjectSdStageId());
			if (test != null) {
				throw new Exception("Project SD Stage already exists");
			}

			projectSdStage = projectSdStageService.save(projectSdStage);
			return ResponseEntity.ok(projectSdStage);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteProjectSdStage(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long projectSdStageId = mu.getAsLongOrNull(data, "projectSdStageId");
		String sql = "CALL ig_db.deleteProjectSdStage(?);";

		System.out.println ("CALL ig_db.deleteProjectSdStage(" + projectSdStageId +");");
		try {	//**//					
			Object[] params = {		
				projectSdStageId	
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