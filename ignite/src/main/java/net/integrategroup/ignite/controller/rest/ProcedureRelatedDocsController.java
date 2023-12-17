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

import net.integrategroup.ignite.persistence.model.ProcedureRelatedDocs;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ProcedureRelatedDocsService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/procedure-related-docs")
public class ProcedureRelatedDocsController {

	@Autowired
	ProcedureRelatedDocsService procedureRelatedDocsService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllProcedureRelatedDocss() {
		try {
			List<ProcedureRelatedDocs> result = procedureRelatedDocsService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveProcedureRelatedDocsNew(@RequestBody ProcedureRelatedDocs procedureRelatedDocs) {
		try {
			ProcedureRelatedDocs test = procedureRelatedDocsService.findByProcedureRelatedDocsId(procedureRelatedDocs.getProcedureRelatedDocsId());
			if (test != null) {
				throw new Exception("The ProcedureRelatedDocs already exists");
			}

			procedureRelatedDocs = procedureRelatedDocsService.save(procedureRelatedDocs);
			return ResponseEntity.ok(procedureRelatedDocs);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteProcedureRelatedDocs(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long procedureRelatedDocsId = mu.getAsLongOrNull(data, "procedureRelatedDocsId");
		String sql = "CALL ig_db.deleteProcedureRelatedDocs(?);";

		System.out.println ("CALL ig_db.deleteProcedureRelatedDocs(" + procedureRelatedDocsId +");");
		try {	//**//					
			Object[] params = {		
				procedureRelatedDocsId	
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
