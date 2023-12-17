package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.RemunerationRateUpline;
import net.integrategroup.ignite.persistence.model.VAgreementParticipants;
import net.integrategroup.ignite.persistence.model.VPPIndividualRatesUpline;
import net.integrategroup.ignite.persistence.model.VRemunerationRateUpline;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.RemunerationRateUplineService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/remuneration-rate-upline")
public class RemunerationRateUplineController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	RemunerationRateUplineService remunerationRateUplineService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping("/{remunerationRateUplineId}")
	public ResponseEntity<?> findByRemunerationRateUplineId(@PathVariable("remunerationRateUplineId") Long remunerationRateUplineId, ModelMap modelMap) {
		try {
			RemunerationRateUpline result = remunerationRateUplineService.findByRemunerationRateUplineId(remunerationRateUplineId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list/individuals/{projectId}")
	public ResponseEntity<?> getPPIndividualListWithTimeCostAgreements(@PathVariable("projectId") Long projectId, ModelMap modelMap) {

		try {
			List<VAgreementParticipants> result = remunerationRateUplineService.getPPIndividualListWithTimeCostAgreements(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/agreement-individuals/{agreementBetweenParticipantsId}")
	public ResponseEntity<?> getAgreementIndividualList(ModelMap modelMap,
														@PathVariable("agreementBetweenParticipantsId") Long agreementBetweenParticipantsId) {
		try {
			List<VAgreementParticipants> result = remunerationRateUplineService.getAgreementIndividualList(agreementBetweenParticipantsId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/agreement-participants/{agreementBetweenParticipantsId}")
	public ResponseEntity<?> getAgreementParticipantList(ModelMap modelMap,
														@PathVariable("agreementBetweenParticipantsId") Long agreementBetweenParticipantsId) {
		try {
			List<VAgreementParticipants> result = remunerationRateUplineService.getAgreementParticipantList(agreementBetweenParticipantsId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}




	@GetMapping("/rates/{agreementBetweenParticipantsId}/{projectParticipantId}")
	public ResponseEntity<?> getRatesUpline(ModelMap modelMap,
														@PathVariable("agreementBetweenParticipantsId") Long agreementBetweenParticipantsId,
														@PathVariable("projectParticipantId") Long projectParticipantId) {
		try {
			List<VRemunerationRateUpline> result = remunerationRateUplineService.getRatesUpline(agreementBetweenParticipantsId, projectParticipantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/rates/current/{agreementBetweenParticipantsId}/{projectParticipantId}")
	public ResponseEntity<?> getRatesUplineCurrent(ModelMap modelMap,
														@PathVariable("agreementBetweenParticipantsId") Long agreementBetweenParticipantsId,
														@PathVariable("projectParticipantId") Long projectParticipantId) {
		try {
			List<VRemunerationRateUpline> result = remunerationRateUplineService.getRatesUplineCurrent(agreementBetweenParticipantsId, projectParticipantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteRemunerationRateUpline(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long remunerationRateUplineId = mu.getAsLongOrNull(data, "remunerationRateUplineId");
		String sql = "CALL ig_db.deleteRemunerationRateUpline(?);";

		System.out.println ("CALL ig_db.deleteRemunerationRateUpline(" + remunerationRateUplineId +");");
		try {	//**//					
			Object[] params = {		
				remunerationRateUplineId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/save-rate")
	public ResponseEntity<?> saveRemunerationRateUpline(@RequestBody Map<String, Object> data) {


		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		MapUtils mu = new MapUtils();
		Long remunerationRateUplineId = mu.getAsLongOrNull(data, "remunerationRateUplineId"); 				//1
		Long agreementBetweenParticipantsId= mu.getAsLongOrNull(data, "agreementBetweenParticipantsId");	//2
		Long projectParticipantSdRoleIdForRate= mu.getAsLongOrNull(data, "projectParticipantSdRoleIdForRate");		//3
		Long participantIdIndividual= mu.getAsLongOrNull(data, "participantIdIndividual");					//4
		Long projBasedRemunTypeId= mu.getAsLongOrNull(data, "projBasedRemunTypeId");				//5
		Double ratePerUnit= mu.getAsDoubleOrNull(data, "ratePerUnit");										//6
		String description= mu.getAsStringOrNull(data, "description");										//7
		Date startDate= mu.getAsDateOrNull(data, "startDate"); 												//8
		Date endDate= mu.getAsDateOrNull(data, "endDate");													//9
		String userName = securityUtils.getUsername();														//10

		try {

			String sql = "CALL ig_db.saveRemunerationRateUpline(?,?,?,?,?,?,?,?,?,?);";

			System.out.println ("CALL ig_db.saveRemunerationRateUpline("
																+ remunerationRateUplineId + ","
																+ agreementBetweenParticipantsId + ","
																+ projectParticipantSdRoleIdForRate + ","
																+ participantIdIndividual + ",'"
																+ projBasedRemunTypeId + "',"
																+ ratePerUnit + ",'"
																+ description + "',"
																+ startDate + ","
																+ endDate + ",'"
																+ userName +"');");
			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				if (remunerationRateUplineId == null) {
					cstm.setNull(1, Types.BIGINT);
				} else {
					cstm.setLong(1, remunerationRateUplineId);
				}
				cstm.setLong(2, agreementBetweenParticipantsId);
	
				if (projectParticipantSdRoleIdForRate == null) {
					cstm.setNull(3, Types.BIGINT);
				} else {
					cstm.setLong(3, projectParticipantSdRoleIdForRate);
				}
				cstm.setLong(4, participantIdIndividual);
	
				if (projBasedRemunTypeId == null) {
					cstm.setNull(5, Types.VARCHAR);
				} else {
					cstm.setLong(5, projBasedRemunTypeId);
				}
	
				if (ratePerUnit == null) {
					cstm.setNull(6, Types.DECIMAL);
				} else {
					cstm.setDouble(6, ratePerUnit);
				}
				if (description == null) {
					cstm.setNull(7, Types.VARCHAR);
				} else {
					cstm.setString(7, description);
				}
				cstm.setDate(8, new java.sql.Date(startDate.getTime()));
	
				if (endDate == null) {
					cstm.setNull(9, Types.DATE);
				} else {
					cstm.setDate(9, new java.sql.Date(endDate.getTime()));
				}
	
				cstm.setString(10, securityUtils.getUsername());
	
				cstm.execute();
				return ResponseEntity.ok().build();
				//**//
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
	//**//

	@GetMapping("/rates-upline/{timesheetId}")
	public ResponseEntity<?> getRatesUplineForTimesheetEntry(ModelMap modelMap,
														@PathVariable("timesheetId") Long timesheetId) {
		try {
			List<VPPIndividualRatesUpline> result = remunerationRateUplineService.getRatesUplineForTimesheetEntry(timesheetId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
