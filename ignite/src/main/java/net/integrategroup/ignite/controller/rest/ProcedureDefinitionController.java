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

import net.integrategroup.ignite.persistence.model.ProcedureDefinition;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ProcedureDefinitionService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/procedure-definition")
public class ProcedureDefinitionController {

	@Autowired
	ProcedureDefinitionService procedureDefinitionService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllProcedureDefinitions() {
		try {
			List<ProcedureDefinition> result = procedureDefinitionService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveProcedureDefinitionNew(@RequestBody ProcedureDefinition procedureDefinition) {
		try {
			ProcedureDefinition test = procedureDefinitionService.findByProcedureDefinitionId(procedureDefinition.getProcedureDefinitionId());
			if (test != null) {
				throw new Exception("The ProcedureDefinition already exists");
			}

			procedureDefinition = procedureDefinitionService.save(procedureDefinition);
			return ResponseEntity.ok(procedureDefinition);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteProcedureDefinition(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long procedureDefinitionId = mu.getAsLongOrNull(data, "procedureDefinitionId");
		String sql = "CALL ig_db.deleteProcedureDefinition(?);";

		System.out.println ("CALL ig_db.deleteProcedureDefinition(" + procedureDefinitionId +");");
		try {	//**//					
			Object[] params = {		
					procedureDefinitionId	
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
