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

import net.integrategroup.ignite.persistence.model.ResourceRemunType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ResourceRemunTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/resource-remun-type")
public class ResourceRemunTypeController {

	@Autowired
	ResourceRemunTypeService resourceRemunTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping("")
	public ResponseEntity<?> findAll() {
		try {
			List<ResourceRemunType> result = resourceRemunTypeService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveResourceRemunType(@RequestBody ResourceRemunType resourceRemunType) {
		try {
			ResourceRemunType test = resourceRemunTypeService
					.findByResourceRemunTypeId(resourceRemunType.getResourceRemunTypeId());
			if (test == null) {
				throw new Exception("Remuneration Type not found");
			}

			resourceRemunType = resourceRemunTypeService.save(resourceRemunType);
			return ResponseEntity.ok(resourceRemunType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveResourceRemunTypeNew(@RequestBody ResourceRemunType resourceRemunType) {
		try {
			ResourceRemunType test = resourceRemunTypeService
					.findByResourceRemunTypeId(resourceRemunType.getResourceRemunTypeId());
			if (test != null) {
				throw new Exception("Remuneration Type already exists.");
			}

			resourceRemunType = resourceRemunTypeService.save(resourceRemunType);
			return ResponseEntity.ok(resourceRemunType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/delete")
	public ResponseEntity<?> deleteResourceRemunType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long resourceRemunTypeId = mu.getAsLongOrNull(data, "resourceRemunTypeId");
		String sql = "CALL ig_db.deleteResourceRemunType(?);";

		System.out.println ("CALL ig_db.deleteResourceRemunType(" + resourceRemunTypeId +");");
		try {	//**//					
			Object[] params = {		
				resourceRemunTypeId	
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
