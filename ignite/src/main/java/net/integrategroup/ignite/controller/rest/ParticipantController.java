package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.integrategroup.ignite.persistence.model.Participant;
import net.integrategroup.ignite.persistence.model.VParticipant;
import net.integrategroup.ignite.persistence.model.VParticipantMin;
import net.integrategroup.ignite.persistence.service.ContactPointService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.KeyValuePairService;
import net.integrategroup.ignite.persistence.service.ParticipantOfficeService;
import net.integrategroup.ignite.persistence.service.ParticipantService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/participant")
public class ParticipantController {

	@Autowired
	ParticipantService participantService;

	@Autowired
	ParticipantOfficeService participantOfficeService;

	@Autowired
	ContactPointService contactPointService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	KeyValuePairService keyValuePairService;

	@Autowired
	SecurityUtils securityUtils;

	
	@GetMapping("/only-me/{participantId}")    //me, and Participants linked to me thru projects
	public ResponseEntity<?> findParticipantOnlyMe(@PathVariable("participantId") Long participantId) {
		try {
			List<VParticipantMin> participants = participantService.findParticipantOnlyMe(participantId);
			return ResponseEntity.ok(participants);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
		
	@GetMapping("/only-me-financial/{participantId}")    //me, and Participants where I am Representative or Marketing
	public ResponseEntity<?> findParticipantOnlyMeFinancial(@PathVariable("participantId") Long participantId) {
		try {
			List<VParticipantMin> participants = participantService.findParticipantOnlyMeFinancial(participantId);
			return ResponseEntity.ok(participants);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
			
	
	
	
	
	
	@GetMapping("/all-in-view")                //all
	public ResponseEntity<?> findAllParticipantsInView() {
		try {
			List<VParticipantMin> participants = participantService.findAllParticipantsInView();
			return ResponseEntity.ok(participants);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	@GetMapping("/individuals-only-me/{participantId}")    //me
	public ResponseEntity<?> findAllIndividualParticipantsInViewOnlyMe(@PathVariable("participantId") Long participantId) {
		try {
			List<VParticipantMin> participants = participantService.findAllIndividualParticipantsInViewOnlyMe(participantId);
			return ResponseEntity.ok(participants);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/individuals-in-view")          //all
	public ResponseEntity<?> findAllIndividualParticipantsInView() {
		try {
			List<VParticipantMin> participants = participantService.findAllIndividualParticipantsInView();
			return ResponseEntity.ok(participants);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	@GetMapping("/view-info/{participantId}")
	public ResponseEntity<?> findInViewByParticipantId(@PathVariable("participantId") Long participantId) {

		try {
			VParticipant result = participantService.findInViewByParticipantId(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}


//	@GetMapping("/all") Rather use view! /all-in-view
//	public ResponseEntity<?> getParticipants() {
//
//		try {
//			List<Participant> participants = participantService.findAll();
//			return ResponseEntity.ok(participants);
//
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//
//	}

	@GetMapping()
	public ResponseEntity<?> getNonIndividualParticipants() {

		try {
			List<VParticipantMin> participants = participantService.findAllNonIndividualParticipants();
			return ResponseEntity.ok(participants);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	
	@GetMapping("{participantId}")
	public ResponseEntity<?> getParticipant(@PathVariable("participantId") Long participantId) {

		try {
			VParticipant participant = participantService.findInViewByParticipantId(participantId); //findByParticipantId(participantId);
			return ResponseEntity.ok(participant);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	
	@GetMapping("/info/{participantId}")
	public ResponseEntity<?> findByParticipantId(@PathVariable("participantId") Long participantId) {

		try {
			Participant result = participantService.findByParticipantId(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	
	@PostMapping()
	public ResponseEntity<?> saveParticipant(@RequestBody Participant participant) {
		try {
			Participant test = participantService.findByParticipantId(participant.getParticipantId());
			if (test == null) {
				throw new Exception("Participant not found");
			}
			participant = participantService.save(participant);

			return ResponseEntity.ok(participant);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@PostMapping("/new-individual")
//	public ResponseEntity<?> saveNewParticipant(@RequestBody Map<String, Object> data) {
//		Long individualId = null;
//		Long participantId = null;
//		Long systemMemberId = null;
//		Long contactPointId = null;
//		Long participantOfficeId = null;
//		Individual individual = null;
//		Participant participant = null;
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
//			String exampleSql = "CALL ig_db.saveNewIndividualParticipant('" + jsonString + "', '" + securityUtils.getUsername()
//					+ "', individualId"
//					+ " , participantId"
//					+ " , systemMemberId"
//					+ " , contactPointId "
//					+ " , participantOfficeId "
//					+ ");";
//			System.out.println("\n\n\n" + exampleSql + "\n\n\n");
//
//			String sql = "CALL ig_db.saveNewIndividual(?, ?, ?, ?, ?, ?, ?);";
//
//			try (CallableStatement cstm = databaseService.prepareCall(sql)) {
//				cstm.setLong(1, jsonString);
//				cstm.setString(2, securityUtils.getUsername());
//				cstm.registerOutParameter(3, java.sql.Types.BIGINT);
//				cstm.registerOutParameter(4, java.sql.Types.BIGINT);
//				cstm.registerOutParameter(5, java.sql.Types.BIGINT);
//				cstm.registerOutParameter(6, java.sql.Types.BIGINT);
//				cstm.registerOutParameter(7, java.sql.Types.BIGINT);
//
//				cstm.execute();
//				individualId = cstm.getLong(3);
//				participantId = cstm.getLong(4);
//				systemMemberId = cstm.getLong(5);
//				contactPointId = cstm.getLong(6);
//				participantOfficeId = cstm.getLong(6);
//			}
//
//			if (individualId != null) {
//				individual = individualService.findByIndividualId(individualId);
//			}
//			return ResponseEntity.ok(individual);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}




//	@PostMapping("/new")
//	public ResponseEntity<?> saveParticipantNew (@RequestBody Participant participant) {
//		try {
//			Participant test = participantService.findByParticipantId(participant.getParticipantId());
//			if (test != null) {
//				throw new Exception("The Participant already exists");
//			}
//			participant = participantService.save(participant);
//
//			return ResponseEntity.ok(participant);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}



// Johannes het hierdie vervang
//	@PostMapping("/delete")
//	public ResponseEntity<?> deleteParticipant(@RequestBody HashMap<String, Object> data) {
//		try {
//			MapUtils mu = new MapUtils();
//
//			Long participantId = mu.getAsLongOrNull(data, "participantId");
//			Participant participant = participantService.findByParticipantId(participantId);
//			participantService.delete(participant);
//
//			return ResponseEntity.ok().build();
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteParticipant(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long participantId = mu.getAsLongOrNull(data, "participantId");
		String sql = "CALL ig_db.deleteParticipant(?);";

		System.out.println ("CALL ig_db.deleteParticipant(" + participantId +");");
		try {	//**//					
			Object[] params = {		
				participantId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}






	/*
	@GetMapping("/office/contact-points/{participantOfficeId}")
	public ResponseEntity<?> getParticipantOfficeContactPoints(
			@PathVariable("participantOfficeId") Long participantOfficeId) {
		try {
			List<ContactPointView> contactPoints = participantOfficeService
					.findContactPointsByParticipantOfficeId(participantOfficeId);

			return ResponseEntity.ok(contactPoints);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	*/

	/*
	@PostMapping("/office/delete")
	public ResponseEntity<?> deleteParticipantOffice(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			Long participantOfficeId = mu.getAsLongOrNull(data, "participantOfficeId");
			participantOfficeService.delete(participantOfficeId);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	*/



	/*
	@PostMapping("/wizard")
	public ResponseEntity<?> saveWizardParticipant(@RequestBody Map<String, Object> data) {
		Long participantId = null;
		Participant participant = null;
		final String PASSWORD_KEY = "pass";

		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveWizardParticipant('" + jsonString + "', '" + securityUtils.getUsername()
			+ "', participantId);";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "CALL ig_db.saveWizardParticipant(?, ?, ?);";

			try (CallableStatement cstm = databaseService.prepareCall(sql)) {
				cstm.setLong(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);

				cstm.execute();
				participantId = cstm.getLong(3);
			}

			if (participantId != null) {
				participant = participantService.findByParticipantId(participantId);
			}

			return ResponseEntity.ok(participant);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	*/
	@GetMapping("/orphan")
	public ResponseEntity<?> getOrphanParticipants() {
		try {
			List<Participant> result = participantService.getOrphanParticipants();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/participant-participation-type")
	public ResponseEntity<?> saveParticipantParticipationType(@RequestBody Map<String, Object> data) throws JsonProcessingException {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveParticipantParticipationTypes('" + jsonString + "', '"
					+ securityUtils.getUsername() + "');";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

			String sql = "CALL ig_db.saveParticipantParticipationTypes(?, ?);";

			try {										//**//
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

	@PostMapping("/new-non-individual")
	public ResponseEntity<?> saveNewNonIndivParticipant(@RequestBody Map<String, Object> data) throws JsonProcessingException {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		Long projectParticipantId = null;
		Long participantId = null;
		Long participantOfficeId = null;
		Long contactPointId = null;
		VParticipant vParticipant = null;

		try {

			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveNewNonIndivParticipant('" + jsonString + "', '" + securityUtils.getUsername()
					+ "', @projectParticipantId"
					+ ", @participantId"
					+ ", @contactPointId "
					+ ", @participantOfficeId "
					+ ");";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "{CALL ig_db.saveNewNonIndivParticipant(?, ?, ?, ?, ?, ?)}";

			try {											//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);
				cstm.registerOutParameter(4, java.sql.Types.BIGINT);
				cstm.registerOutParameter(5, java.sql.Types.BIGINT);
				cstm.registerOutParameter(6, java.sql.Types.BIGINT);

				cstm.execute();
				projectParticipantId = cstm.getLong(3);
				participantId = cstm.getLong(4);
				contactPointId = cstm.getLong(5);
				participantOfficeId = cstm.getLong(6);
				
			if (participantId != null) {
				vParticipant = participantService.findInViewByParticipantId(participantId);
			}
			return ResponseEntity.ok(vParticipant);
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

	@PostMapping("/existing-non-individual")
	public ResponseEntity<?> saveExistingNonIndividualParticipant(@RequestBody Map<String, Object> data) throws JsonProcessingException {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		Long projectParticipantId = null;
		Long participantId = null;
		Long participantOfficeId = null;
		Long contactPointId = null;
		VParticipant vParticipant = null;

		try {

			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveNonIndivParticipant('" + jsonString + "', '" + securityUtils.getUsername()
					+ "', projectParticipantId"
					+ " , participantId"
					+ " , contactPointId "
					+ " , participantOfficeId "
					+ ");";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "{CALL ig_db.saveNonIndivParticipant(?, ?, ?, ?, ?, ?)}";

			try {											//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);
				cstm.registerOutParameter(4, java.sql.Types.BIGINT);
				cstm.registerOutParameter(5, java.sql.Types.BIGINT);
				cstm.registerOutParameter(6, java.sql.Types.BIGINT);

				cstm.execute();
				projectParticipantId = cstm.getLong(3);
				participantId = cstm.getLong(4);
				contactPointId = cstm.getLong(5);
				participantOfficeId = cstm.getLong(6);

				if (participantId != null) {
				vParticipant = participantService.findInViewByParticipantId(participantId);
			}
			return ResponseEntity.ok(vParticipant);
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






	@GetMapping("/get-next-invoice-number/{participantId}")
	public ResponseEntity<?> getNextInvoiceNumber(ModelMap modelMap,
														@PathVariable("participantId") Long participantId) {


		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		String errorMessage = "Unknown error has occured";

		try {

			String exampleSql = "CALL ig_db.getNextInvoiceNumber(" + participantId
					+ ", @pNextInvoiceNumber, @vInvoiceNumberFormat, @vInvoicePrefix, @vYearMonthPart, @vLatestInvoiceNumber, @vDigitLength);";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "CALL ig_db.getNextInvoiceNumber(?, ?, ?, ?, ?, ?, ?);";

			String pNextInvoiceNumber;
			String vInvoiceNumberFormat;
			String vInvoicePrefix;
			String vYearMonthPart;
			Long   vLatestInvoiceNumber;
			Long   vDigitLength;

			try {											//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setLong(1, participantId);

				cstm.registerOutParameter(2, java.sql.Types.VARCHAR);
				cstm.registerOutParameter(3, java.sql.Types.VARCHAR);
				cstm.registerOutParameter(4, java.sql.Types.VARCHAR);
				cstm.registerOutParameter(5, java.sql.Types.VARCHAR);
				cstm.registerOutParameter(6, java.sql.Types.BIGINT);
				cstm.registerOutParameter(7, java.sql.Types.INTEGER);

				cstm.execute();
				pNextInvoiceNumber   = cstm.getString(2);
				vInvoiceNumberFormat = cstm.getString(3);
				vInvoicePrefix       = cstm.getString(4);
				vYearMonthPart       = cstm.getString(5);
				vLatestInvoiceNumber = cstm.getLong(6);
				vDigitLength         = cstm.getLong(7);

				JSONObject jo = new JSONObject();
				jo.put("pNextInvoiceNumber", pNextInvoiceNumber);
				jo.put("vInvoiceNumberFormat", vInvoiceNumberFormat);
				jo.put("vInvoicePrefix", vInvoicePrefix);
				jo.put("vYearMonthPart", vYearMonthPart);
				jo.put("vLatestInvoiceNumber", vLatestInvoiceNumber);
				jo.put("vDigitLength", vDigitLength);


				return ResponseEntity.ok(jo.toString(4));
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



	@PostMapping("/set-last-invoice-number/{participantId}/{newNumber}")
	public ResponseEntity<?> setLastInvoiceNumberOnParticipant(
			@PathVariable("participantId") Long participantId,
			@PathVariable("newNumber") Long newNumber) {


		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//


		try {
			String sql = "CALL ig_db.setLastInvoiceNumberOnParticipant(?, ?);";

			System.out.println ("CALL ig_db.setLastInvoiceNumberOnParticipant(" + participantId + ", " + newNumber + ");");
			
			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setLong(1, participantId);
				cstm.setLong(2, newNumber);
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

	@GetMapping("/representative-of/{individualId}")
	public ResponseEntity<?> getRepesentativeOf(@PathVariable("individualId") Long individualId) {

		try {
			List<VParticipantMin> participants = participantService.getRepesentativeOf(individualId);
			return ResponseEntity.ok(participants);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@GetMapping("/marketing-rep-of/{individualId}")
	public ResponseEntity<?> getMarketingRepOf(@PathVariable("individualId") Long individualId) {

		try {
			List<VParticipantMin> participants = participantService.getMarketingRepOf(individualId);
			return ResponseEntity.ok(participants);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// /rest/ignite/v1/participant/logo/clear
	@PostMapping("/logo/clear")
	public ResponseEntity<?> clearLogo(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();
			Long participantId = mu.getAsLongOrNull(data, "participantId");

			participantService.clearLogo(participantId);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}