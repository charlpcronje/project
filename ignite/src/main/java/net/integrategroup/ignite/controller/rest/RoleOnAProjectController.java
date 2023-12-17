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
import net.integrategroup.ignite.persistence.model.VRoleOnAProject;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.RoleOnAProjectService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/role-on-a-project")
public class RoleOnAProjectController {


	@Autowired
	RoleOnAProjectService roleOnAProjectService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping()
	public ResponseEntity<?> getRoleOnAProject() {
		try {
			List<RoleOnAProject> result = roleOnAProjectService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/all-from-view")
	public ResponseEntity<?> getAllRoleOnAProjectFromView() {
		try {
			List<VRoleOnAProject> result = roleOnAProjectService.getAllRoleOnAProjectFromView();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping()
	public ResponseEntity<?> saveRoleOnAProject(@RequestBody RoleOnAProject roleOnAProject) {
		try {
			RoleOnAProject test = roleOnAProjectService.findByRoleOnAProjectId(roleOnAProject.getRoleOnAProjectId());
			if (test == null) {
				throw new Exception("Role On A Project not found");
			}

			roleOnAProject = roleOnAProjectService.save(roleOnAProject);

			return ResponseEntity.ok(roleOnAProject);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping("/new")
	public ResponseEntity<?> saveRoleOnAProjectNew(@RequestBody RoleOnAProject roleOnAProject) {
		try {
			RoleOnAProject test = roleOnAProjectService.findByRoleOnAProjectId(roleOnAProject.getRoleOnAProjectId());
			if (test != null) {
				throw new Exception("Role on a Project already exists");
			}

			roleOnAProject = roleOnAProjectService.save(roleOnAProject);

			return ResponseEntity.ok(roleOnAProject);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/delete")
	public ResponseEntity<?> deleteRoleOnAProject(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long roleOnAProjectId = mu.getAsLongOrNull(data, "roleOnAProjectId");
		String sql = "CALL ig_db.deleteRoleOnAProject(?);";

		System.out.println ("CALL ig_db.deleteRoleOnAProject(" + roleOnAProjectId + ");");
		try {	//**//					
			Object[] params = {		
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


	@GetMapping("/not-linked/{genProcedureId}")
	public ResponseEntity<?> findRoleOnAProjectNotLinked(@PathVariable("genProcedureId") Long genProcedureId, ModelMap modelMap) {
		try {
			List<RoleOnAProject> result = roleOnAProjectService.findRoleOnAProjectNotLinked(genProcedureId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/not-linked/{serviceDisciplineId}/{serviceDisciplineId2}")
	public ResponseEntity<?> findSdGroupRolesUsedNotLinked(
									@PathVariable("serviceDisciplineId") Long serviceDisciplineId,
									@PathVariable("serviceDisciplineId2") Long serviceDisciplineId2){
		try {
			List<RoleOnAProject> result = roleOnAProjectService.findSdGroupRolesUsedNotLinked(serviceDisciplineId, serviceDisciplineId2);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/list-view-role-on-a-project-for-service-discipline/{serviceDisciplineId}")                           //Find List from View that needs parameter
	public ResponseEntity<?> findListVRoleOnAProjectForServiceDiscipline(@PathVariable("serviceDisciplineId") Long serviceDisciplineId,	ModelMap modelMap)  {
		try {
			List<VRoleOnAProject> result = roleOnAProjectService.findListVRoleOnAProjectForServiceDiscipline(serviceDisciplineId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/level0/{serviceDisciplineId}")
	public ResponseEntity<?> findRolesForSdIndustry(@PathVariable("serviceDisciplineId") Long serviceDisciplineId, ModelMap modelMap) {
		try {
			List<RoleOnAProject> result = roleOnAProjectService.findRolesForSdIndustry(serviceDisciplineId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/for-sd/{serviceDisciplineId}")
	public ResponseEntity<?> findRolesForSd(@PathVariable("serviceDisciplineId") Long serviceDisciplineId, ModelMap modelMap) {
		try {
			List<RoleOnAProject> result = roleOnAProjectService.findRolesForSd(serviceDisciplineId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/for-sd-available/{serviceDisciplineId}/{serviceDisciplineIdIndustry}")
	public ResponseEntity<?> findRolesForSdAvailable(@PathVariable("serviceDisciplineId") Long serviceDisciplineId,
													@PathVariable("serviceDisciplineIdIndustry") Long serviceDisciplineIdIndustry) {
		try {
			List<RoleOnAProject> result = roleOnAProjectService.findRolesForSdAvailable(serviceDisciplineId, serviceDisciplineIdIndustry);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}