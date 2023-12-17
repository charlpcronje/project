package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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

import net.integrategroup.ignite.persistence.model.BankTransactionLink;
import net.integrategroup.ignite.persistence.model.VBankTransactionLink;
import net.integrategroup.ignite.persistence.service.BankTransactionLinkService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/bank-transaction-link")
//				 /rest/ignite/v1/bank-transaction-link/delete/4

public class BankTransactionLinkController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	BankTransactionLinkService bankTransactionLinkService;

	@Autowired
	SecurityUtils securityUtils;


	@GetMapping("/list")
	public ResponseEntity<?> findAll(ModelMap modelMap) {
		try {
			List<BankTransactionLink> result = bankTransactionLinkService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping()
	public ResponseEntity<?> saveBankTransactionLink(@RequestBody BankTransactionLink bankTransactionLink) {
		try {

			BankTransactionLink test = bankTransactionLinkService
					.findByBankTransactionLinkId(bankTransactionLink.getBankTransactionLinkId());
			if (test == null) {
				throw new Exception(" Type not found");
			}

			bankTransactionLink = bankTransactionLinkService.save(test);
			return ResponseEntity.ok(bankTransactionLink);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveBankTransactionLinkNew(@RequestBody BankTransactionLink bankTransactionLink) {
		try {

			BankTransactionLink test = bankTransactionLinkService
					.findByBankTransactionLinkId(bankTransactionLink.getBankTransactionLinkId());
			if (test != null) {
				throw new Exception("BankTransactionLink already exists");
			}

			bankTransactionLink = bankTransactionLinkService.save(bankTransactionLink);
			return ResponseEntity.ok(bankTransactionLink);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}




	@GetMapping("/list-participant-bank-details/{participantBankDetailsId}")
	public ResponseEntity<?> getBankTransactionLinkForParticipantBankDetailsId(ModelMap modelMap, @PathVariable(name = "participantBankDetailsId") Long participantBankDetailsId) {
		try {
			List<VBankTransactionLink> result = bankTransactionLinkService.getBankTransactionLinkForParticipantBankDetailsId(participantBankDetailsId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list-bank-transaction/{bankTransactionId}")
	public ResponseEntity<?> getBankTransactionLinkForBankTransactionId(ModelMap modelMap, @PathVariable(name = "bankTransactionId") Long bankTransactionId) {
		try {
			List<VBankTransactionLink> result = bankTransactionLinkService.getBankTransactionLinkForBankTransactionId(bankTransactionId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}




	@PostMapping("/delete")
	public ResponseEntity<?> deleteBankTransactionLink(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long bankTransactionLinkId = mu.getAsLongOrNull(data, "bankTransactionLinkId");
		String sql = "CALL ig_db.deleteBankTransactionLink(?);";

		System.out.println ("CALL ig_db.deleteBankTransactionLink(" + bankTransactionLinkId + ");");
		try {
			Object[] params = {
					bankTransactionLinkId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}


	@PostMapping("/approve/{participantId}")
	public ResponseEntity<?> approveBankTransactionLink(@RequestBody Map<String, Object> data, @PathVariable("participantId") Long participantId) {

		MapUtils mu = new MapUtils();
		Long bankTransactionLinkId = mu.getAsLongOrNull(data, "bankTransactionLinkId");
		String sql = "CALL ig_db.approveBankTransactionLink(?, ?);";

		System.out.println ("CALL ig_db.approveBankTransactionLink(" + bankTransactionLinkId + ", " + participantId + ");");
		try {
			Object[] params = {
					bankTransactionLinkId,
					participantId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}



	@PostMapping("/do-links/{participantBankDetailsId}/{fromDate}/{toDate}")
	public ResponseEntity<?> linkProjectExpensesToBankTransactions(ModelMap modelMap,
														@PathVariable("participantBankDetailsId") Long participantBankDetailsId,
														@PathVariable("fromDate") Date fromDate,
														@PathVariable("toDate") Date toDate) {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		try {

			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			// ObjectMapper om = new ObjectMapper();

			String exampleSql = "CALL ig_db.doInsertBankTransactionLink(" + participantBankDetailsId
					+ ", '" + fromDate
					+ "','" + toDate
					+ "', @linkedCount);";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "CALL ig_db.doInsertBankTransactionLink(?, ?, ?, ?);";

			Long linkedCount;

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setLong(1, participantBankDetailsId);
				cstm.setDate(2, new java.sql.Date(fromDate.getTime()));
				cstm.setDate(3, new java.sql.Date(toDate.getTime()));
				cstm.registerOutParameter(4, java.sql.Types.BIGINT);

				cstm.execute();
				linkedCount = cstm.getLong(4);

				return ResponseEntity.ok(linkedCount);
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












}

