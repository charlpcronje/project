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

import net.integrategroup.ignite.persistence.model.Permission;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.PermissionService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/permission")
public class PermissionController {

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	PermissionService permissionService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllPermissions() {
		try {
			List<Permission> result = permissionService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> savePermission(@RequestBody Permission permission) {
		try {
			permission = permissionService.save(permission);

			return ResponseEntity.ok(permission);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deletePermission(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long permissionId = mu.getAsLongOrNull(data, "permissionId");
		String sql = "CALL ig_db.deletePermission(?);";

		System.out.println ("CALL ig_db.deletePermission(" + permissionId +");");
		try {	//**//					
			Object[] params = {		
				permissionId	
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
