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

import net.integrategroup.ignite.persistence.model.Definition;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.DefinitionService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/definition")
public class DefinitionController {

	@Autowired
	DefinitionService definitionService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getDefinition() {
		try {
			List<Definition> result = definitionService.getDefinition();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveDefinition(@RequestBody Definition definition) {
		try {

			Definition test = definitionService
					.findByDefinitionId(definition.getDefinitionId());
			if (test == null) {
				throw new Exception(" Type not found");
			}

			definition = definitionService.save(definition);
			return ResponseEntity.ok(definition);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveDefinitionNew(@RequestBody Definition definition) {
		try {

			Definition test = definitionService
					.findByDefinitionId(definition.getDefinitionId());
			if (test != null) {
				throw new Exception("Definition already exists");
			}

			definition = definitionService.save(definition);
			return ResponseEntity.ok(definition);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteDefinition(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long definitionId = mu.getAsLongOrNull(data, "definitionId");
		String sql = "CALL ig_db.deleteDefinition(?);";

		System.out.println ("CALL ig_db.deleteDefinition(" + definitionId +");");
		try {
			Object[] params = {
					definitionId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/not-linked/{genProcedureId}")
	public ResponseEntity<?> findDefinitionNotLinked(@PathVariable("genProcedureId") Long genProcedureId, ModelMap modelMap) {
		try {
			List<Definition> result = definitionService.findDefinitionNotLinked(genProcedureId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}




}
