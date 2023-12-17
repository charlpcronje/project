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

import net.integrategroup.ignite.persistence.model.ResourceType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ResourceTypeService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/resource-type")
public class ResourceTypeController {

	@Autowired
	ResourceTypeService resourceTypeService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getResourceType() {
		try {
			List<ResourceType> result = resourceTypeService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveResourceType(@RequestBody ResourceType resourceType) {
		try {

			ResourceType test = resourceTypeService
					.findByResourceTypeId(resourceType.getResourceTypeId());
			if (test == null) {
				throw new Exception("Resource Type not found");
			}

			resourceType = resourceTypeService.save(resourceType);
			return ResponseEntity.ok(resourceType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveResourceTypeNew(@RequestBody ResourceType resourceType) {
		try {
			ResourceType test = resourceTypeService
					.findByResourceTypeId(resourceType.getResourceTypeId());
			if (test != null) {
				throw new Exception("The Resource Type already exists");
			}

			resourceType = resourceTypeService.save(resourceType);
			return ResponseEntity.ok(resourceType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteResourceType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long resourceTypeId = mu.getAsLongOrNull(data, "resourceTypeId");
		String sql = "CALL ig_db.deleteResourceType(?);";

		System.out.println ("CALL ig_db.deleteResourceType(" + resourceTypeId +");");
		try {	//**//					
			Object[] params = {		
				resourceTypeId	
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
