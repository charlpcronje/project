package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.integrategroup.ignite.persistence.model.VParticipantBankDetails;
import net.integrategroup.ignite.persistence.service.BranchService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ParticipantBankDetailsService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/participant-bank-details")
public class ParticipantBankDetailsController {

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	ParticipantBankDetailsService participantBankDetailsService;

	@Autowired
	BranchService branchService;

	@Autowired
	DatabaseService databaseService;

//	@GetMapping("/individual/{id}")
//	public ResponseEntity<?> getParticipantBankDetailsForIndividual(@PathVariable("id") Long individualId) {
//		try {
//			List<ParticipantBankDetails> result = participantBankDetailsService
//					.getParticipantBankDetailsForIndividual(individualId);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}


	@GetMapping("/project/{id}")
	public ResponseEntity<?> findByProjectId(@PathVariable("id") Long projectId) {
		try {
			List<VParticipantBankDetails> result = participantBankDetailsService.findByProjectId(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@GetMapping("/participant/{participantId}")
	public ResponseEntity<?> findParticipantBankDetails(@PathVariable("participantId") Long participantId) {
		try {
			List<VParticipantBankDetails> result = participantBankDetailsService.findParticipantBankDetails(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveParticipantBankDetails(@RequestBody Map<String, Object> data) throws JsonProcessingException {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveParticipantBankDetails('" + jsonString + "', '"
					+ securityUtils.getUsername() + "');";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

			String sql = "CALL ig_db.saveParticipantBankDetails(?, ?);";

			try {											//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
			
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());

				cstm.execute();

			return ResponseEntity.ok().build();
			//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} finally {							//**//
			try {							//**//
				if (cstm != null) {			//**//
					cstm.close();			//**//
				}							//**//
											//**//
				if (conn != null) {			//**//
					conn.close();			//**//
				}							//**//
			} catch (SQLException e) {		//**//
				e.printStackTrace();		//**//
			}								//**//
		}									//**//
	}										//**//	
	//**//
	

	
	
//	@PostMapping("/new")
//	public ResponseEntity<?> saveParticipantBankDetailsNew(@RequestBody ParticipantBankDetails participantBankDetails) {
//		try {
//
//			ParticipantBankDetails test = participantBankDetailsService.findByParticipantBankDetailsId(participantBankDetails.getParticipantBankDetailsId());
//			if (test != null) {
//				throw new Exception("Participant Bank Details already exists");
//			}
//
//			participantBankDetails = participantBankDetailsService.save(participantBankDetails);
//			return ResponseEntity.ok(participantBankDetails);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}


	@PostMapping("/delete")
	public ResponseEntity<?> deleteParticipantBankDetails(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long participantBankDetailsId = mu.getAsLongOrNull(data, "participantBankDetailsId");
		String sql = "CALL ig_db.deleteParticipantBankDetails(?);";

		System.out.println ("CALL ig_db.deleteParticipantBankDetails(" + participantBankDetailsId+");");
		try {	//**//					
			Object[] params = {		
					participantBankDetailsId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}


//	private PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//

//	@PostMapping("/new-participant-bank-details")
//	public ResponseEntity<?> saveNewParticipantBankDetails(@RequestBody Map<String, Object> data) {
//		Long participantBankDetailsId = null;
//		ParticipantBankDetails participantBankDetails = null;
//		// Participant participant = null;
//		final String PASSWORD_KEY = "pass";
//
//		try {
//			// NOTE: if there is a password we have to encrypt it
//			if (data.containsKey(PASSWORD_KEY)) {
//				MapUtils mu = new MapUtils();
//				String pwd = mu.getAsStringOrNull(data, PASSWORD_KEY);
//				String encryptedPwd = passwordEncoder().encode(pwd);
//				data.put(PASSWORD_KEY, encryptedPwd);
//			}
//
//			// Convert data into a JSON string (note: there will be numeric fields that must
//			// not be quoted!!)
//			ObjectMapper om = new ObjectMapper();
//			String jsonString = om.writeValueAsString(data);
//
//			String exampleSql = "CALL ig_db.saveNewParticipantBankDetails('" + jsonString + "', '" + securityUtils.getUsername()
//					+ "', participantBankDetailsId"
//					+ ");";
//			System.out.println("\n\n\n" + exampleSql + "\n\n\n");
//
//			String sql = "CALL ig_db.saveNewParticipantBankDetails(?, ?, ?);";
//
//			try (CallableStatement cstm = databaseService.prepareCall(sql)) {
//				cstm.setString(1, jsonString);
//				cstm.setString(2, securityUtils.getUsername());
//				cstm.registerOutParameter(3, java.sql.Types.BIGINT);
//
//				cstm.execute();
//				participantBankDetailsId = cstm.getLong(3);
//			}
//
////			if (individualId != null) {
////				individual = individualService.findByIndividualId(individualId);
////			}
//			if (participantBankDetailsId != null) {
//				participantBankDetails = participantBankDetailsService.findByParticipantBankDetailsId(participantBankDetailsId);
//			}
//			return ResponseEntity.ok(participantBankDetails);
//		} catch (Exception e) {
//			e.printStackTrace();	
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//
//
//	@PostMapping("/update-participant-bank-details")
//	public ResponseEntity<?> saveUpdateParticipantBankDetails(@RequestBody Map<String, Object> data) {
//		Long participantBankDetailsId = null;
//		ParticipantBankDetails participantBankDetails = null;
//		Participant Participant = null;
//		final String PASSWORD_KEY = "pass";
//
//		try {
//			// NOTE: if there is a password we have to encrypt it
//			if (data.containsKey(PASSWORD_KEY)) {
//				MapUtils mu = new MapUtils();
//				String pwd = mu.getAsStringOrNull(data, PASSWORD_KEY);
//				String encryptedPwd = passwordEncoder().encode(pwd);
//				data.put(PASSWORD_KEY, encryptedPwd);
//			}
//
//			// Convert data into a JSON string (note: there will be numeric fields that must
//			// not be quoted!!)
//			ObjectMapper om = new ObjectMapper();
//			String jsonString = om.writeValueAsString(data);
//
//			String exampleSql = "CALL ig_db.saveUpdateParticipantBankDetails('" + jsonString + "', '" + securityUtils.getUsername()
//					+ "', participantBankDetailsId"
//					+ ");";
//			System.out.println("\n\n\n" + exampleSql + "\n\n\n");
//
//			String sql = "CALL ig_db.saveUpdateParticipantBankDetails(?, ?, ?);";
//
//			try (CallableStatement cstm = databaseService.prepareCall(sql)) {
//				cstm.setString(1, jsonString);
//				cstm.setString(2, securityUtils.getUsername());
//				cstm.registerOutParameter(3, java.sql.Types.BIGINT);
//
//				cstm.execute();
//				participantBankDetailsId = cstm.getLong(3);
//			}
//
////			if (individualId != null) {
////				individual = individualService.findByIndividualId(individualId);
////			}
//			if (participantBankDetailsId != null) {
//				participantBankDetails = participantBankDetailsService.findByParticipantBankDetailsId(participantBankDetailsId);
//			}
//			return ResponseEntity.ok(participantBankDetails);
//		} catch (Exception e) {
//			e.printStackTrace();	
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}



}
