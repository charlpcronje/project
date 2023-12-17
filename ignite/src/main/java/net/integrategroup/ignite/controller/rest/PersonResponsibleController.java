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

import net.integrategroup.ignite.persistence.model.PersonResponsible;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.PersonResponsibleService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/person-responsible")
public class PersonResponsibleController {

	@Autowired
	PersonResponsibleService personResponsibleService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllPersonResponsibles() {
		try {
			List<PersonResponsible> result = personResponsibleService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> savePersonResponsibleNew(@RequestBody PersonResponsible personResponsible) {
		try {
			PersonResponsible test = personResponsibleService.findByPersonResponsibleId(personResponsible.getPersonResponsibleId());
			if (test != null) {
				throw new Exception("The PersonResponsible already exists");
			}

			personResponsible = personResponsibleService.save(personResponsible);
			return ResponseEntity.ok(personResponsible);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deletePersonResponsible(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long personResponsibleId = mu.getAsLongOrNull(data, "personResponsibleId");
		String sql = "CALL ig_db.deletePersonResponsible(?);";

		System.out.println ("CALL ig_db.deletePersonResponsible(" + personResponsibleId +");");
		try {	//**//					
			Object[] params = {		
				personResponsibleId	
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
