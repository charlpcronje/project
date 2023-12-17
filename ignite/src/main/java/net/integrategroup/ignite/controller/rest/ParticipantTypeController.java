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

import net.integrategroup.ignite.persistence.model.ParticipantType;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ParticipantTypeService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/participant-type")
public class ParticipantTypeController {

	@Autowired
	ParticipantTypeService participantTypeService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> findAll() {
		try {
			List<ParticipantType> result = participantTypeService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("non-indiv")
	public ResponseEntity<?> findAllNonIndiv() {
		try {
			List<ParticipantType> result = participantTypeService.findAllNonIndiv();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveParticipantType(@RequestBody ParticipantType participantType) {
		try {

			ParticipantType test = participantTypeService
					.findByParticipantTypeCode(participantType.getParticipantTypeCode());
			if (test == null) {
				throw new Exception("Participant Type not found");
			}

			participantType = participantTypeService.save(participantType);
			return ResponseEntity.ok(participantType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveParticipantTypeNew(@RequestBody ParticipantType participantType) {
		try {
			ParticipantType test = participantTypeService
					.findByParticipantTypeCode(participantType.getParticipantTypeCode());
			if (test != null) {
				throw new Exception("The Participant Type already exists");
			}

			participantType = participantTypeService.save(participantType);
			return ResponseEntity.ok(participantType);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteParticipantType(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		String participantTypeCode = mu.getAsStringOrNull(data, "participantTypeCode");
		String sql = "CALL ig_db.deleteParticipantType(?);";

		System.out.println ("CALL ig_db.deleteParticipantType(" + participantTypeCode +");");
		try {	//**//					
			Object[] params = {		
				participantTypeCode	
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
