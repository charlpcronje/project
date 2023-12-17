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

import net.integrategroup.ignite.persistence.model.RoleOnAProject;
import net.integrategroup.ignite.persistence.model.SdRole;
import net.integrategroup.ignite.persistence.model.VSdRole;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.SdRoleService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/sd-role")
public class SdRoleController {

	@Autowired
	SdRoleService sdRoleService;



	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllSdRoles() {
		try {
			List<SdRole> result = sdRoleService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/all-from-view")
	public ResponseEntity<?> findAllVSdRole() {
		try {
			List<VSdRole> result = sdRoleService.findAllVSdRole();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveSdRole(@RequestBody SdRole sdRole) {
		try {

			SdRole test = sdRoleService.findBySdRoleId(sdRole.getSdRoleId());
			if (test == null) {
				throw new Exception("SdRole not found");
			}

			sdRole = sdRoleService.save(sdRole);
			return ResponseEntity.ok(sdRole);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveSdRoleNew(@RequestBody SdRole sdRole) {
		try {
			SdRole test = sdRoleService.findBySdRoleId(sdRole.getSdRoleId());
			if (test != null) {
				throw new Exception("The Sd-Role already exists");
			}

			sdRole = sdRoleService.save(sdRole);
			return ResponseEntity.ok(sdRole);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/delete")
	public ResponseEntity<?> deleteSdRole(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long sdRoleId = mu.getAsLongOrNull(data, "sdRoleId");
		String sql = "CALL ig_db.deleteSdRole(?);";

		System.out.println ("CALL ig_db.deleteSdRole(" + sdRoleId +");");
		try {	//**//					
			Object[] params = {		
				sdRoleId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/delete2")
	public ResponseEntity<?> deleteSdRole2(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long serviceDisciplineId = mu.getAsLongOrNull(data, "serviceDisciplineId");
		Long roleOnAProjectId = mu.getAsLongOrNull(data, "roleOnAProjectId");
		String sql = "CALL ig_db.deleteSdRole2(?, ?);";

		System.out.println ("CALL ig_db.deleteSdRole2(" + serviceDisciplineId + ", " + roleOnAProjectId + ");");
		try {	//**//					
			Object[] params = {		
				serviceDisciplineId,
				roleOnAProjectId
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/{sdRoleId}")
	public ResponseEntity<?> findBySdRoleId(@PathVariable("sdRoleId") Long sdRoleId,
			ModelMap modelMap) {
		try {
			SdRole result = sdRoleService.findBySdRoleId(sdRoleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/list-roles-for-sd/{serviceDisciplineId}")
	public ResponseEntity<?> findByServiceDisciplineId(@PathVariable("serviceDisciplineId") Long serviceDisciplineId, ModelMap modelMap) {
		try {
			List<SdRole> result = sdRoleService.findByServiceDisciplineId(serviceDisciplineId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list-for-project-sd/{projectSdId}")
	public ResponseEntity<?> findRolesByProjectSdId(@PathVariable("projectSdId") Long projectSdId, ModelMap modelMap) {
		try {
			List<RoleOnAProject> result = sdRoleService.findRolesByProjectSdId(projectSdId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/list-for-project-sd/available/{projectSdId}/{projectParticipantId}")
	public ResponseEntity<?> findRolesAvailableByProjectSdId(	@PathVariable("projectSdId") Long projectSdId,
																@PathVariable("projectParticipantId") Long projectParticipantId,
																ModelMap modelMap) {
		try {
			List<RoleOnAProject> result = sdRoleService.findRolesAvailableByProjectSdId(projectSdId, projectParticipantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



}
