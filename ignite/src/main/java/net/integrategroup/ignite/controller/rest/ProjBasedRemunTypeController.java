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

import net.integrategroup.ignite.persistence.model.ProjBasedRemunType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ProjBasedRemunTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/proj-based-remun-type")
public class ProjBasedRemunTypeController {

	@Autowired
	ProjBasedRemunTypeService projBasedRemunTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllProjBasedRemunType() {
		try {
			List<ProjBasedRemunType> result = projBasedRemunTypeService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{projBasedRemunTypeId}")
	public ResponseEntity<?> getProjBasedRemunType(ModelMap modelMap, @PathVariable(name = "projBasedRemunTypeId") Long projBasedRemunTypeId) {
		try {
			ProjBasedRemunType result = projBasedRemunTypeService.findByProjBasedRemunTypeId(projBasedRemunTypeId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/time-based")
	public ResponseEntity<?> getTimeBasedProjBasedRemunType() {
		try {
			List<ProjBasedRemunType> result = projBasedRemunTypeService.getTimeBasedProjBasedRemunType();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveProjBasedRemunType(@RequestBody ProjBasedRemunType projBasedRemunType) {
		try {
			ProjBasedRemunType test = projBasedRemunTypeService
					.findByProjBasedRemunTypeId(projBasedRemunType.getProjBasedRemunTypeId());
			if (test == null) {
				throw new Exception("Project Based Remuneration Type not found");
			}

			projBasedRemunType = projBasedRemunTypeService.save(projBasedRemunType);
			return ResponseEntity.ok(projBasedRemunType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveProjBasedRemunTypeNew(@RequestBody ProjBasedRemunType projBasedRemunType) {
		try {
			ProjBasedRemunType test = projBasedRemunTypeService
					.findByProjBasedRemunTypeId(projBasedRemunType.getProjBasedRemunTypeId());
			if (test != null) {
				throw new Exception("The Project Based Remuneration Type already exists");
			}

			projBasedRemunType = projBasedRemunTypeService.save(projBasedRemunType);
			return ResponseEntity.ok(projBasedRemunType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteProjBasedRemunType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long projBasedRemunTypeId = mu.getAsLongOrNull(data, "projBasedRemunTypeId");
		String sql = "CALL ig_db.deleteProjBasedRemunType(?);";

		System.out.println ("CALL ig_db.deleteProjBasedRemunType(" + projBasedRemunTypeId +");");
		try {	//**//					
			Object[] params = {		
					projBasedRemunTypeId	
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
