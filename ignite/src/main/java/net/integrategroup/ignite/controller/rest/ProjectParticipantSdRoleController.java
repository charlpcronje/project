package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.integrategroup.ignite.persistence.model.ProjectParticipantSdRole;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ProjectParticipantSdRoleService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/project-participant-sd-role")
public class ProjectParticipantSdRoleController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ProjectParticipantSdRoleService projectParticipantSdRoleService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping("/{projectParticipantId}")
	public ResponseEntity<?> getProjectParticipantSdList(@PathVariable("projectParticipantId") Long projectParticipantId, ModelMap modelMap) {

		try {
			List<ProjectParticipantSdRole> result = projectParticipantSdRoleService.getProjectParticipantSdList(projectParticipantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveProjectParticipantSd(@RequestBody ProjectParticipantSdRole projectParticipantSdRole) {
		try {

			ProjectParticipantSdRole test = projectParticipantSdRoleService.findByProjectParticipantSdRoleId(projectParticipantSdRole.getProjectParticipantSdRoleId());
			if (test == null) {
				throw new Exception("Project Participant Service Discipline not found");
			}

			projectParticipantSdRole = projectParticipantSdRoleService.save(projectParticipantSdRole);
			return ResponseEntity.ok(projectParticipantSdRole);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@PostMapping("/new")
//	public ResponseEntity<?> saveProjectParticipantSdNew(@RequestBody ProjectParticipantSdRole projectParticipantSd) {
//		try {
//
//			ProjectParticipantSdRole test = projectParticipantSdRoleService.findByProjectParticipantSdRoleId(projectParticipantSd.getProjectParticipantSdRoleId());
//			if (test != null) {
//				throw new Exception("Project Participant Service Discipline already exists");
//			}
//
//			projectParticipantSd = projectParticipantSdRoleService.save(projectParticipantSd);
//			return ResponseEntity.ok(projectParticipantSd);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@PostMapping("/new")
	public ResponseEntity<?> saveNewPPSdRole(@RequestBody Map<String, Object> data) throws JsonProcessingException
	{

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		Long projectParticipantSdRoleId = null;
		// Long projectSdId = null;
		ProjectParticipantSdRole projectParticipantSdRole = null;

		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveNewPPSdRole('" + jsonString + "', '"
					+ securityUtils.getUsername()
					+ "', projectParticipantSdRoleId"
					+ "');";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

			String sql = "CALL ig_db.saveNewPPSdRole(?, ?, ?);";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//

				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);

				cstm.execute();

				projectParticipantSdRoleId = cstm.getLong(3);

				return ResponseEntity.ok(projectParticipantSdRoleId);
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


		@PostMapping("/delete")
	public ResponseEntity<?> deleteProjectParticipantSd(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long projectParticipantSdRoleId = mu.getAsLongOrNull(data, "projectParticipantSdRoleId");
		String sql = "CALL ig_db.deleteProjectParticipantSdRole(?);";

		System.out.println ("CALL ig_db.deleteProjectParticipantSdRole(" + projectParticipantSdRoleId +");");
		try {	//**//					
			Object[] params = {		
				projectParticipantSdRoleId	
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
