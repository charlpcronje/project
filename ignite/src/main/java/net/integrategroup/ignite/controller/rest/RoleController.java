package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.util.HashMap;
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

import net.integrategroup.ignite.persistence.model.Permission;
import net.integrategroup.ignite.persistence.model.Role;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.RolePermissionService;
import net.integrategroup.ignite.persistence.service.RoleService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/role")
public class RoleController {

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	RoleService roleService;

	@Autowired
	RolePermissionService rolePermissionService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllRoles() {
		try {
			List<Role> result = roleService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveRole(@RequestBody Role role) {
		try {
			role = roleService.save(role);

			return ResponseEntity.ok(role);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("{roleCode:.+}/available")
	public ResponseEntity<?> getAvailablePermissions(@PathVariable("roleCode") String roleCode) {
		try {
			List<Permission> result = roleService.findAvailablePermissions(roleCode);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("{roleCode:.+}/assigned")
	public ResponseEntity<?> getAssignedPermissions(@PathVariable("roleCode") String roleCode) {
		try {
			List<Permission> result = roleService.findAssignedPermissions(roleCode);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/assign")
	public ResponseEntity<?> assignPermissionToRole(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			String roleCode = mu.getAsStringOrNull(data, "roleCode");
			String permissionCode = mu.getAsStringOrNull(data, "permissionCode");

			rolePermissionService.assign(roleCode, permissionCode);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping("/revoke")
	public ResponseEntity<?> revokePermission(@RequestBody HashMap<String, Object> data) {
		MapUtils mu = new MapUtils();

		try {
			String roleCode = mu.getAsStringOrNull(data, "roleCode");
			String permissionCode = mu.getAsStringOrNull(data, "permissionCode");

			rolePermissionService.revoke(roleCode, permissionCode);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteRole(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		String roleCode = mu.getAsStringOrNull(data, "roleCode");
		String sql = "CALL ig_db.deleteRole(?);";

		System.out.println ("CALL ig_db.deleteRole(" + roleCode +");");
		try {	//**//					
			Object[] params = {		
				roleCode	
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
