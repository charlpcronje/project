package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.integrategroup.ignite.persistence.model.ContactPoint;
import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.IndividualCompetency;
import net.integrategroup.ignite.persistence.model.Participant;
import net.integrategroup.ignite.persistence.model.ParticipantOffice;
import net.integrategroup.ignite.persistence.model.SystemMember;
import net.integrategroup.ignite.persistence.model.VIndividual;
import net.integrategroup.ignite.persistence.model.VParticipant;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.IndividualService;
import net.integrategroup.ignite.persistence.service.ParticipantService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

/**
 * @author Ingrid Marais
 *
 */
@RestController
@RequestMapping("/rest/ignite/v1/individual")
public class IndividualController {

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	IndividualService individualService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ParticipantService participantService;

	@GetMapping("/list")
	public ResponseEntity<?> getIndividualList() {
		try {
			List<VIndividual> result = individualService.findAllIndividualsInView();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/competency/{individualId}/{serviceDisciplineId}")
	public ResponseEntity<?> getIndividualCompetencyRoles(
									@PathVariable("individualId") Long individualId,
									@PathVariable("serviceDisciplineId") Long serviceDisciplineId) {
		try {

			List<IndividualCompetency> result = individualService.getIndividualCompetencyRoles(individualId, serviceDisciplineId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	//	@GetMapping("/select-indiv-list")
//	public ResponseEntity<?> findAll() {
//		try {
//			List<Individual> result = individualService.findAll();
//
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}


	@GetMapping("/unique/{username}")
	public ResponseEntity<?> getUniqueFlag(@PathVariable("username") String username) {
		try {
			Map<String, Object> result = new HashMap<>();

			Integer count = individualService.getUsernameUsageCount(username);
			if (count == null) {
				// this should never occur...
				count = -1;
			}

			result.put("userName", username);
			result.put("unique", count == 0);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/unique/{id}/{username}")
	public ResponseEntity<?> getUniqueFlagForExisting(@PathVariable("id") Long individualId,
			@PathVariable("username") String username) {
		try {
			Map<String, Object> result = new HashMap<>();

			Integer count = individualService.getUsernameUsageCount(individualId, username);
			if (count == null) {
				// this should never occur...
				count = -1;
			}

			result.put("userName", username);
			result.put("unique", count == 0);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/additional/{individualId}")
	public ResponseEntity<?> getAdditionalDetailsForIndividual(@PathVariable("individualId") Long individualId) {
		try {
			Participant result = individualService.getParticipantForIndividual(individualId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	@GetMapping("/user-name-id")
	public ResponseEntity<?> getLoggedIndividualId() {
		try {
			String userNameLoggedIn = securityUtils.getUsername();
			Individual result = individualService.getLoggedIndividualId(userNameLoggedIn);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	@GetMapping("/system-member/{individualId}")
	public ResponseEntity<?> getSystemMemberIdForNewIndividual(@PathVariable("individualId") Long individualId) {
		try {
			SystemMember result = individualService.getSystemMemberForIndividual(individualId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteIndividual(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long individualId = mu.getAsLongOrNull(data, "individualId");
		String sql = "CALL ig_db.deleteIndividual(?);";

		System.out.println ("CALL ig_db.deleteIndividual(" + individualId +");");
		try {
			Object[] params = {
				individualId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}


	@PostMapping("/additional")
	public ResponseEntity<?> saveAdditional(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			Long participantId = mu.getAsLongOrNull(data, "participantId");
			String systemName = mu.getAsStringOrNull(data, "systemName");
			String registeredName = mu.getAsStringOrNull(data, "registeredName");
			String tradingName = mu.getAsStringOrNull(data, "tradingName");
			String tapSubscriptionCode = mu.getAsStringOrNull(data, "tapSubscriptionCode");
			Long marketingIndividualId = mu.getAsLongOrNull(data, "marketingIndividualId");
			Long representativeIndividualId = mu.getAsLongOrNull(data, "representativeIndividualId");
			Long latestProjectNumber = mu.getAsLongOrNull(data, "latestProjectNumber");
			Integer defaultInvoiceDay = mu.getAsIntegerOrNull(data, "defaultInvoiceDay");
			Long participantOfficeIdDefault = mu.getAsLongOrNull(data, "participantOfficeIdDefault");
			Long participantBankDetailsIdDefault = mu.getAsLongOrNull(data, "participantBankDetailsIdDefault");

			if (participantId == null) {
				throw new Exception("Could not determine the Participant Id."); // Always goes to catch, which will exit
				// this method
			}

			Participant participant = participantService.findByParticipantId(participantId);
			if (participant == null) {
				throw new Exception("Participant could not be found."); // Always goes to catch, which will exit this
				// method
			}

			participant.setRegisteredName(registeredName);
			participant.setSystemName(systemName);
			participant.setTradingName(tradingName);
			participant.setTapSubscriptionCode(tapSubscriptionCode);
			participant.setMarketingIndividualId(marketingIndividualId);
			participant.setRepresentativeIndividualId(representativeIndividualId);
			participant.setLatestProjectNumber(latestProjectNumber);
			participant.setDefaultInvoiceDay(defaultInvoiceDay);
			participant.setParticipantOfficeIdDefault(participantOfficeIdDefault);
			participant.setParticipantBankDetailsIdDefault(participantBankDetailsIdDefault);
			participant = participantService.save(participant);

			return ResponseEntity.ok(participant);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/contact-point/{id}")
	public ResponseEntity<?> getContactPointForIndividual(@PathVariable("id") Long individualId) {
		try {
			ContactPoint result = individualService.getContactPointForIndividual(individualId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/offices/{id}")
	public ResponseEntity<?> getParticipantAndResourceIds(@PathVariable("id") Long individualId) {
		try {
			List<ParticipantOffice> result = individualService.getParticipantOfficesForIndividual(individualId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping()
	public ResponseEntity<?> saveIndividual(@RequestBody Individual individual) {
		try {

			Individual test = individualService.findByIndividualId(individual.getIndividualId());
			if (test == null) {
				throw new Exception("Individual not found");
			}

			individual = individualService.save(individual);
			return ResponseEntity.ok(individual);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@PostMapping("/new-participant")
	public ResponseEntity<?> saveNewIndividualParticipant(@RequestBody Map<String, Object> data) throws JsonProcessingException {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//
	
		Long individualId = null;
		Long projectParticipantId = null;
		Long participantId = null;
		Long contactPointId = null;
		Long systemMemberId = null;
		Long participantOfficeId = null;
		Individual individual = null;
		VParticipant vParticipant = null;
		final String PASSWORD_KEY = "pass";

		try {

			// NOTE: if there is a password we have to encrypt it
			if (data.containsKey(PASSWORD_KEY)) {
				MapUtils mu = new MapUtils();
				String pwd = mu.getAsStringOrNull(data, PASSWORD_KEY);
				if ((pwd != null) && (!pwd.isEmpty())) {
					String encryptedPwd = passwordEncoder().encode(pwd);
					data.put(PASSWORD_KEY, encryptedPwd);
				}
			}

			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveNewIndividualParticipant('" + jsonString + "', '" + securityUtils.getUsername()
					+ "', @individualId"
					+ ", @projectParticipantId"
					+ ", @participantId"
					+ ", @systemMemberId"
					+ ", @contactPointId"
					+ ", @participantOfficeId"
					+ ");";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "{CALL ig_db.saveNewIndividualParticipant(?, ?, ?, ?, ?, ?, ?, ?)}";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
	
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);
				cstm.registerOutParameter(4, java.sql.Types.BIGINT);
				cstm.registerOutParameter(5, java.sql.Types.BIGINT);
				cstm.registerOutParameter(6, java.sql.Types.BIGINT);
				cstm.registerOutParameter(7, java.sql.Types.BIGINT);
				cstm.registerOutParameter(8, java.sql.Types.BIGINT);

				cstm.execute();
				individualId = cstm.getLong(3);
				projectParticipantId = cstm.getLong(4);
				participantId = cstm.getLong(5);
				systemMemberId = cstm.getLong(6);
				contactPointId = cstm.getLong(7);
				participantOfficeId = cstm.getLong(8);

				if (participantId != null) {
				vParticipant = participantService.findInViewByParticipantId(participantId);
			}
			return ResponseEntity.ok(vParticipant);
		} catch (Exception e) {
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

	
	
	@PostMapping("/save-login")
	public ResponseEntity<?> saveLoginPass(@RequestBody Map<String, Object> data) throws JsonProcessingException {
		final String PASSWORD_KEY = "pass";
		Connection conn = null;
		CallableStatement cstm = null;
		String jsonString = null;

		try {

			// NOTE: if there is a password we have to encrypt it
			if (data.containsKey(PASSWORD_KEY)) {
				MapUtils mu = new MapUtils();
				String pwd = mu.getAsStringOrNull(data, PASSWORD_KEY);
				if ((pwd != null) && (!pwd.isEmpty())) {
					String encryptedPwd = passwordEncoder().encode(pwd);
					data.put(PASSWORD_KEY, encryptedPwd);
				}
			}

			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveLoginPass('" 
						+ jsonString + "', '" 
						+ securityUtils.getUsername() + ");";

			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "{CALL ig_db.saveLoginPass(?, ?)}";

			try {
				conn = databaseService.getConnection();
				cstm = conn.prepareCall(sql);
				
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());

				cstm.execute();
				return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} finally {
			try {
				if (cstm != null) {
					cstm.close();
				}
				
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	@PostMapping("/save-remove-login")
	public ResponseEntity<?> saveRemoveLoginPass(@RequestBody Map<String, Object> data) throws JsonProcessingException {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//
		String jsonString = null;

		try {

			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveRemoveLoginPass('" + jsonString + "', '" + securityUtils.getUsername() + ");";

			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "{CALL ig_db.saveRemoveLoginPass(?, ?)}";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());

				cstm.execute();
				return ResponseEntity.ok().build();
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
	
	
	
	@PostMapping("/existing-participant")
	public ResponseEntity<?> saveExistingIndividualParticipant(@RequestBody Map<String, Object> data) throws JsonProcessingException {
	
		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//
		String jsonString = null;

		Long individualId = null;
		Long projectParticipantId = null;
		Long participantId = null;
		Long systemMemberId = null;
		Long participantOfficeId = null;
		Long contactPointId = null;
		VParticipant vParticipant = null;
		final String PASSWORD_KEY = "pass";

		System.out.println("\n\n\n existing-participant \n\n\n");
		try {
			// NOTE: if there is a password we have to encrypt it
			if (data.containsKey(PASSWORD_KEY)) {
				MapUtils mu = new MapUtils();
				String pwd = mu.getAsStringOrNull(data, PASSWORD_KEY);
				String encryptedPwd = passwordEncoder().encode(pwd);
				data.put(PASSWORD_KEY, encryptedPwd);
			}

			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveIndividualParticipant('" + jsonString + "', '" + securityUtils.getUsername()
					+ "', @individualId"
					+ ", @projectParticipantId"
					+ ", @participantId"
					+ ", @systemMemberId"
					+ ", @contactPointId"
					+ ", @participantOfficeId"
					+ ");";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "{CALL ig_db.saveIndividualParticipant(?, ?, ?, ?, ?, ?, ?, ?)}";   //String sql = "CALL ig_db.saveIndividualParticipant(?, ?, ?, ?, ?, ?, ?, ?);";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);
				cstm.registerOutParameter(4, java.sql.Types.BIGINT);
				cstm.registerOutParameter(5, java.sql.Types.BIGINT);
				cstm.registerOutParameter(6, java.sql.Types.BIGINT);
				cstm.registerOutParameter(7, java.sql.Types.BIGINT);
				cstm.registerOutParameter(8, java.sql.Types.BIGINT);

				cstm.execute();
				individualId = cstm.getLong(3);
				projectParticipantId = cstm.getLong(4);
				participantId = cstm.getLong(5);
				systemMemberId = cstm.getLong(6);
				contactPointId = cstm.getLong(7);
				participantOfficeId = cstm.getLong(8);

				if (participantId != null) {
				vParticipant = participantService.findInViewByParticipantId(participantId);
				}
				return ResponseEntity.ok(vParticipant);
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

	
	
	@PostMapping("/sd/delete")
	public ResponseEntity<?> deleteIndividualSd(@RequestBody Map<String, Object> data) {
		MapUtils mu = new MapUtils();

		Long individualSdId = mu.getAsLongOrNull(data, "IndividualSdId");
		try {
			individualService.deleteIndividualSd(individualSdId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/competency/delete")
	public ResponseEntity<?> deleteIndividualCompetency(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long individualCompetencyId = mu.getAsLongOrNull(data, "individualCompetencyId");
		String sql = "CALL ig_db.deleteIndividualCompetency(?);";

		System.out.println ("CALL ig_db.deleteIndividualCompetency(" + individualCompetencyId + ");");
		
		try {	//**//					
			Object[] params = {		
					individualCompetencyId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			//**//
				return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

	@PostMapping("/sd-competency")
	public ResponseEntity<?> saveIndividualSdandCompetency(@RequestBody Map<String, Object> data) throws JsonProcessingException {
		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//
		String jsonString = null;

		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveIndividualCompetency('" + jsonString + "', '" + securityUtils.getUsername()+ ");";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "CALL ig_db.saveIndividualCompetency(?, ?);";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());

				cstm.execute();
				return ResponseEntity.ok().build();
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

	/*
	@PostMapping("operational-service-disciplines")
	public ResponseEntity<?> saveOperationalServiceDisciplines(@RequestBody Map<String, Object> data) {
		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveOperationalServiceDisciplines('" + jsonString + "', '"
					+ securityUtils.getUsername() + "');";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

			String sql = "CALL ig_db.saveOperationalServiceDisciplines(?, ?);";

			try (CallableStatement cstm = databaseService.prepareCall(sql)) {
				cstm.setLong(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());

				cstm.execute();
			}

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	*/

//	@PostMapping("/repair")
//	public ResponseEntity<?> repairIndividual(@RequestBody Map<String, Object> data) {
//		try {
//			MapUtils mu = new MapUtils();
//
//			Long individualId = mu.getAsLongOrNull(data, "individualId");
//			Long participantId = mu.getAsLongOrNull(data, "participantId");
//
//			Resource resource = new Resource();
//
//			resource.setIndividualId(individualId);
//			resource.setParticipantId(participantId);
//			resource.setIsActiveMember(IgniteConstants.FLAG_YES);
//			resource.setRoleCode(IgniteConstants.DEFAULT_ROLE);
//
//			resourceService.save(resource);
//
//			return ResponseEntity.ok().build();
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

//	@GetMapping("/resources-not-selected-yet/{participantId}")
//	public ResponseEntity<?> getParticipantResourcesNotSelectedYet(@PathVariable("participantId") Long participantId) {
//		try {
//			List<Individual> participants = individualService.findParticipantResourcesNotSelectedYet(participantId);
//
//			return ResponseEntity.ok(participants);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	// @GetMapping("/contact-points/{individualId}")
	// public ResponseEntity<?> getContactPoints(@PathVariable("individualId") Long
	// individualId) {
	// try {
	// List<ContactPoint> result = null;
	//
	// // TODO: get the contact points for this individual
	// throw new Exception("complete this code: We need to remodel the DB to allow
	// us to get an individuals contact points");
	//
	// //return ResponseEntity.ok(result);
	// } catch (Exception e) {
	// return ResponseEntity.badRequest().body(e.getMessage());
	// }
	// }
}
