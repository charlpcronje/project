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

import net.integrategroup.ignite.persistence.model.Permission;
import net.integrategroup.ignite.persistence.model.Role;
import net.integrategroup.ignite.persistence.model.RolePermission;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.RolePermissionService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/role-permission")
public class RolePermissionController {

	@Autowired
	RolePermissionService rolePermissionService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;




	@GetMapping("/by-role/{roleCode}")
	public ResponseEntity<?> getRolePermissionbyRole(@PathVariable("roleCode") String roleCode) {
		try {
			List<RolePermission> result = rolePermissionService.getRolePermissionbyRole(roleCode);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/by-permission/{permissionCode}")
	public ResponseEntity<?> getRolePermissionbyPermission(@PathVariable("permissionCode") String permissionCode) {
		try {
			List<RolePermission> result = rolePermissionService.getRolePermissionbyPermission(permissionCode);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@GetMapping("/permission-not-linked/{roleCode}")
	public ResponseEntity<?> getPermissionNotLinked(@PathVariable("roleCode") String roleCode) {
		try {
			List<Permission> result = rolePermissionService.getPermissionNotLinked(roleCode);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/role-not-linked/{permissionCode}")
	public ResponseEntity<?> getRoleNotLinked(@PathVariable("permissionCode") String permissionCode) {
		try {
			List<Role> result = rolePermissionService.getRoleNotLinked(permissionCode);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}











	@PostMapping("/new")
	public ResponseEntity<?> saveRolePermissionNew(@RequestBody RolePermission rolePermission) {
		try {
			RolePermission test = rolePermissionService.findByRolePermissionId(rolePermission.getRolePermissionId());
			if (test != null) {
				throw new Exception("The RolePermission already exists");
			}

			rolePermission = rolePermissionService.save(rolePermission);
			return ResponseEntity.ok(rolePermission);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/delete")
	public ResponseEntity<?> deleteRolePermission(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		String rolePermissionCode = mu.getAsStringOrNull(data, "rolePermissionCode");
		String sql = "CALL ig_db.deleteRolePermission(?);";

		System.out.println ("CALL ig_db.deleteRolePermission(" + rolePermissionCode +");");
		try {	//**//					
			Object[] params = {		
				rolePermissionCode	
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
