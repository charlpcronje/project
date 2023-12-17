package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.integrategroup.ignite.persistence.model.ProjectStage;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ProjectStageService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/project-stage")
public class ProjectStageController {

	@Autowired
	public ProjectStageService projectStageService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping("/list")
	public ResponseEntity<?> findAll() {
		try {
			List<ProjectStage> result = projectStageService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveProjectStage(@RequestBody ProjectStage projectStage) {
		try {

			ProjectStage test = projectStageService.findByProjectStageId(projectStage.getProjectStageId());
			if (test == null) {
				throw new Exception("Project Stage not found");
			}

			projectStage = projectStageService.save(projectStage);
			return ResponseEntity.ok(projectStage);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping("/delete")
	public ResponseEntity<?> deleteProjectStage(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long projectStageId = mu.getAsLongOrNull(data, "projectStageId");
		String sql = "CALL ig_db.deleteProjectStage(?);";

		System.out.println ("CALL ig_db.deleteProjectStage(" + projectStageId+");");
		try {	//**//					
			Object[] params = {		
				projectStageId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

	@PostMapping("/new")
	public ResponseEntity<?> saveProjectStageNew(@RequestBody Map<String, Object> data) throws JsonProcessingException
	{

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		Long projectStageId = null;

		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveProjectStageNew('" + jsonString + "', '"
					+ securityUtils.getUsername()
					+ "', projectStageId"
					+ ");";

			System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

			String sql = "CALL ig_db.saveProjectStageNew(?, ?, ?);";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				

				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);

				cstm.execute();

				projectStageId= cstm.getLong(3);

				return ResponseEntity.ok(projectStageId);
				//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} finally {							
			try {							
				if (cstm != null) {			
					cstm.close();			
				}							
											
				if (conn != null) {			
					conn.close();			
				}							
			} catch (SQLException e) {		
				e.printStackTrace();		
			}								
		}									
	}											
	//**//
}
