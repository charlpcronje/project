package net.integrategroup.ignite.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.SoqTemplate;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.SoqTemplateService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/soq-template")
public class SoqTemplateController {
	
	@Autowired
	SoqTemplateService soqTemplateService;
	
	@Autowired
	DatabaseService databaseService;

	@GetMapping("/all")
	public ResponseEntity<?> getTemplates() {
		
		try {
			List<SoqTemplate> templates = soqTemplateService.findAll();
			return ResponseEntity.ok(templates);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping()
	public ResponseEntity<?> saveTemplate(@RequestBody SoqTemplate soqTemplate) {
		try {

			SoqTemplate test = soqTemplateService.findByTemplateId(soqTemplate.getSoqTemplateId());
			if (test == null) {
				throw new Exception("Project Expense not found");
			}

			soqTemplate = soqTemplateService.save(soqTemplate);
			return ResponseEntity.ok(soqTemplate);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/new")
	public ResponseEntity<?> saveTemplateNew(@RequestBody SoqTemplate soqTemplate) {
		try {
			SoqTemplate test = soqTemplateService.findByTemplateId(soqTemplate.getSoqTemplateId());
			if (test != null) {
				throw new Exception("Template already exists.");
			}
			soqTemplate = soqTemplateService.save(soqTemplate);

			return ResponseEntity.ok(soqTemplate);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteTemplate(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long templateId = mu.getAsLongOrNull(data, "soqTemplateId");
		String sql = "CALL ig_db.deleteSoqTemplate(?);";

		System.out.println ("CALL ig_db.deleteSoqTemplate(" + templateId +");");
		try {	//**//					
			Object[] params = {		
				templateId	
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
