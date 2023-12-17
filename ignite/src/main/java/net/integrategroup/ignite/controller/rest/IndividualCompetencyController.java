package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
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

import net.integrategroup.ignite.persistence.model.IndividualCompetency;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.IndividualCompetencyService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/individualCompetency")
public class IndividualCompetencyController {

	@Autowired
	IndividualCompetencyService individualCompetencyService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllIndividualCompetencys() {
		try {
			List<IndividualCompetency> result = individualCompetencyService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveIndividualCompetency(@RequestBody IndividualCompetency individualCompetency) {
		try {

			IndividualCompetency test = individualCompetencyService.findByIndividualCompetencyId(individualCompetency.getIndividualCompetencyId());
			if (test == null) {
				throw new Exception("IndividualCompetency not found");
			}

			individualCompetency = individualCompetencyService.save(individualCompetency);
			return ResponseEntity.ok(individualCompetency);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveIndividualCompetencyNew(@RequestBody IndividualCompetency individualCompetency) {
		try {
			IndividualCompetency test = individualCompetencyService.findByIndividualCompetencyId(individualCompetency.getIndividualCompetencyId());
			if (test != null) {
				throw new Exception("The IndividualCompetency already exists");
			}

			individualCompetency = individualCompetencyService.save(individualCompetency);
			return ResponseEntity.ok(individualCompetency);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}





	@PostMapping("/delete")
	public ResponseEntity<?> deleteIndividualCompetency(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long individualCompetencyId = mu.getAsLongOrNull(data, "individualCompetencyId");
		String sql = "CALL ig_db.deleteIndividualCompetency(?);";

		System.out.println ("CALL ig_db.deleteIndividualCompetency(" + individualCompetencyId +");");
		try {
			Object[] params = {
				individualCompetencyId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/individual-sd-role/{id}")
	public ResponseEntity<?> getIndividualCompetencyForIndividualSdRole(@PathVariable("id") Long individualSdRoleId) {
		try {
			List<IndividualCompetency> result = individualCompetencyService.getIndividualCompetencyForIndividualSdRole(individualSdRoleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}