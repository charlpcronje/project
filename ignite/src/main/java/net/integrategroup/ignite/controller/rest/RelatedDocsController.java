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

import net.integrategroup.ignite.persistence.model.RelatedDocs;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.RelatedDocsService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/related-docs")
public class RelatedDocsController {

	@Autowired
	RelatedDocsService relatedDocsService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getRelatedDocs() {
		try {
			List<RelatedDocs> result = relatedDocsService.getRelatedDocs();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveRelatedDocs(@RequestBody RelatedDocs relatedDocs) {
		try {

			RelatedDocs test = relatedDocsService
					.findByRelatedDocsId(relatedDocs.getRelatedDocsId());
			if (test == null) {
				throw new Exception(" Type not found");
			}

			relatedDocs = relatedDocsService.save(relatedDocs);
			return ResponseEntity.ok(relatedDocs);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveRelatedDocsNew(@RequestBody RelatedDocs relatedDocs) {
		try {

			RelatedDocs test = relatedDocsService
					.findByRelatedDocsId(relatedDocs.getRelatedDocsId());
			if (test != null) {
				throw new Exception("RelatedDocs already exists");
			}

			relatedDocs = relatedDocsService.save(relatedDocs);
			return ResponseEntity.ok(relatedDocs);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteRelatedDocs(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long relatedDocsId = mu.getAsLongOrNull(data, "relatedDocsId");
		String sql = "CALL ig_db.deleteRelatedDocs(?);";

		System.out.println ("CALL ig_db.deleteRelatedDocs(" + relatedDocsId +");");
		try {	//**//					
			Object[] params = {		
				relatedDocsId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/not-linked/{genProcedureId}")
	public ResponseEntity<?> findRelatedDocsNotLinked(@PathVariable("genProcedureId") Long genProcedureId, ModelMap modelMap) {
		try {
			List<RelatedDocs> result = relatedDocsService.findRelatedDocsNotLinked(genProcedureId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}




}
