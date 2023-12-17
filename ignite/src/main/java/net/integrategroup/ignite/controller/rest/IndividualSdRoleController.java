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

import net.integrategroup.ignite.persistence.model.IndividualSdRole;
import net.integrategroup.ignite.persistence.model.VIndividualSdRole;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.IndividualSdRoleService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/individual-sd-role")
public class IndividualSdRoleController {

	@Autowired
	IndividualSdRoleService individualSdRoleService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllIndividualSdRoles() {
		try {
			List<IndividualSdRole> result = individualSdRoleService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveIndividualSdRole(@RequestBody IndividualSdRole individualSdRole) {
		try {

			IndividualSdRole test = individualSdRoleService.findByIndividualSdRoleId(individualSdRole.getIndividualSdRoleId());
			if (test == null) {
				throw new Exception("IndividualSdRole not found");
			}

			individualSdRole = individualSdRoleService.save(individualSdRole);
			return ResponseEntity.ok(individualSdRole);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveIndividualSdRoleNew(@RequestBody IndividualSdRole individualSdRole) {
		try {
			IndividualSdRole test = individualSdRoleService.findByIndividualSdRoleId(individualSdRole.getIndividualSdRoleId());
			if (test != null) {
				throw new Exception("The IndividualSdRole already exists");
			}

			individualSdRole = individualSdRoleService.save(individualSdRole);
			return ResponseEntity.ok(individualSdRole);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}





	@PostMapping("/delete")
	public ResponseEntity<?> deleteIndividualSdRole(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long individualSdRoleId = mu.getAsLongOrNull(data, "individualSdRoleId");
		String sql = "CALL ig_db.deleteIndividualSdRole(?);";

		System.out.println ("CALL ig_db.deleteIndividualSdRole(" + individualSdRoleId +");");
		try {	//**//					
			Object[] params = {		
				individualSdRoleId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@GetMapping("/participant/{id}")
	public ResponseEntity<?> findByParticipant(@PathVariable("id") Long participantId) {
		try {
			List<VIndividualSdRole> result = individualSdRoleService.findByParticipant(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


//	@GetMapping("/weet nie/{individualSdRoleId}")
//	public ResponseEntity<?> findByIndividualSdRoleId(@PathVariable("individualSdRoleId") Long individualSdRoleId) {
//
//		try {
//			IndividualSdRole result = individualSdRoleService.findByIndividualSdRoleId(individualSdRoleId);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//
//	}










}