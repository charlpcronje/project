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

import net.integrategroup.ignite.persistence.model.UnitType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.UnitTypeService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/unit-type")
public class UnitTypeController {

	@Autowired
	public UnitTypeService unitTypeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> findAll() {
		try {
			List<UnitType> result = unitTypeService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveUnitType(@RequestBody UnitType unitType) {
		try {

			UnitType test = unitTypeService.findByUnitTypeCode(unitType.getUnitTypeCode());
			if (test == null) {
				throw new Exception("Unit Type not found");
			}

			test.setName(unitType.getName());
			test.setDescription(unitType.getDescription());

			unitType = unitTypeService.save(test);
			return ResponseEntity.ok(unitType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveUnitTypeNew(@RequestBody UnitType unitType) {
		try {

			UnitType test = unitTypeService.findByUnitTypeCode(unitType.getUnitTypeCode());
			if (test != null) {
				throw new Exception("The Unit Type already exists");
			}

			unitType = unitTypeService.save(unitType);
			return ResponseEntity.ok(unitType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteUnitType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		String unitTypeCode = mu.getAsStringOrNull(data, "unitTypeCode");
		String sql = "CALL ig_db.deleteUnitType(?);";

		System.out.println ("CALL ig_db.deleteUnitType(" + unitTypeCode +");");
		try {	//**//					
			Object[] params = {		
				unitTypeCode	
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
