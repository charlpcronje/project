package net.integrategroup.ignite.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.Participant;
import net.integrategroup.ignite.persistence.model.SoqTemplate;
import net.integrategroup.ignite.persistence.model.SoqTemplateSubSchedule;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.SoqTemplateSubScheduleService;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@RequestMapping("/rest/ignite/v1/soq-template-subschedule")
public class SoqTemplateSubScheduleController {
	
	@Autowired
	SoqTemplateSubScheduleService soqTemplateSubScheduleService;
	
	@Autowired
	DatabaseService databaseService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getSubSchedules() {
		
		try {
			List<SoqTemplateSubSchedule> subSchedules = soqTemplateSubScheduleService.findAll();
			return ResponseEntity.ok(subSchedules);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/info/{templateId}")
	public ResponseEntity<?> findByTemplateId(@PathVariable("templateId") Long templateId) {

		try {
			List<SoqTemplateSubSchedule> result = soqTemplateSubScheduleService.findSubScheduleByTemplateId(templateId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	@PostMapping()
	public ResponseEntity<?> saveTemplate(@RequestBody SoqTemplateSubSchedule soqTemplateSubSchedule) {
		try {

			SoqTemplateSubSchedule test = soqTemplateSubScheduleService.findBySubScheduleId(soqTemplateSubSchedule.getSoqTemplateId());
			if (test == null) {
				throw new Exception("Project Expense not found");
			}

			soqTemplateSubSchedule = soqTemplateSubScheduleService.save(soqTemplateSubSchedule);
			return ResponseEntity.ok(soqTemplateSubSchedule);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/new")
	public ResponseEntity<?> saveTemplateNew(@RequestBody SoqTemplateSubSchedule soqTemplateSubSchedule) {
		try {
			SoqTemplateSubSchedule test = soqTemplateSubScheduleService.findBySubScheduleId(soqTemplateSubSchedule.getSoqTemplateId());
			if (test != null) {
				throw new Exception("Template already exists.");
			}
			soqTemplateSubSchedule = soqTemplateSubScheduleService.save(soqTemplateSubSchedule);

			return ResponseEntity.ok(soqTemplateSubSchedule);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteTemplateSubSchedule(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long templateSubScheduleId = mu.getAsLongOrNull(data, "soqTemplSubScheduleId");
		String sql = "CALL ig_db.deleteSoqTemplateSubSchedule(?);";

		System.out.println ("CALL ig_db.deleteSoqTemplateSubSchedule(" + templateSubScheduleId +");");
		try {	//**//					
			Object[] params = {		
				templateSubScheduleId	
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
