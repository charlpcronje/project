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

import net.integrategroup.ignite.persistence.model.ParticipantOffice;
import net.integrategroup.ignite.persistence.service.ContactPointService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ParticipantOfficeService;
import net.integrategroup.ignite.persistence.service.ParticipantService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/participant-office")
public class ParticipantOfficeController {

	@Autowired
	ParticipantService participantService;

	@Autowired
	ParticipantOfficeService participantOfficeService;

	@Autowired
	ContactPointService contactPointService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;


	@GetMapping("/{participantId}")
	public ResponseEntity<?> getParticipantOffices(@PathVariable("participantId") Long participantId) {
		try {
			List<ParticipantOffice> participants = participantOfficeService.findByParticipantId(participantId);

			return ResponseEntity.ok(participants);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveParticipantOffice(@RequestBody ParticipantOffice participantOffice) {
		try {
			ParticipantOffice test = participantOfficeService.findByParticipantOfficeId(participantOffice.getParticipantOfficeId());
			if (test == null) {
				throw new Exception("Participant Office not found");
			}

			participantOffice = participantOfficeService.save(participantOffice);

			return ResponseEntity.ok(participantOffice);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveParticipantOfficeNew(@RequestBody ParticipantOffice participantOffice) {
		try {
			ParticipantOffice test = participantOfficeService.findByParticipantOfficeId(participantOffice.getParticipantOfficeId());
			if (test != null) {
				throw new Exception("Participant Office already exists");
			}

			participantOffice = participantOfficeService.save(participantOffice);

			return ResponseEntity.ok(participantOffice);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteParticipantOffice(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long participantOfficeId = mu.getAsLongOrNull(data, "participantOfficeId");
		String sql = "CALL ig_db.deleteParticipantOffice(?);";

		System.out.println ("CALL ig_db.deleteParticipantOffice(" + participantOfficeId +");");
		try {	//**//					
			Object[] params = {		
					participantOfficeId	
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