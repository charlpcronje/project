package net.integrategroup.ignite.controller.rest;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
//import java.sql.Date;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.integrategroup.ignite.persistence.model.BankStatement;
import net.integrategroup.ignite.persistence.service.BankStatementService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/bank-statement")
public class BankStatementController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	BankStatementService bankStatementService;

	@Autowired
	SecurityUtils securityUtils;


//	@PostMapping("/insert-bank-statement")   ///{assignmentTypeId}/{assignmentId}")
//	public ResponseEntity<?> doInsertBankStatement(@RequestBody Map<String, Object> data) {
//		try {
//			MapUtils mu = new MapUtils();
//			Long   participantBankDetailsId = mu.getAsLongOrNull(data, "participantBankDetailsId");
//			String description1 = mu.getAsStringOrNull(data, "description1");
//			Date   statementDate = mu.getAsDateOrNull(data, "statementDate");
//			Double amount = mu.getAsDoubleOrNull(data, "amount");
//			String sql = "CALL ig_db.doInsertBankStatement(?, ?, ?, ?);";
//
//	//		System.out.println ("CALL ig_db.doInsertBankStatement(" + participantBankDetailsId + ", " + description1 + ", " + statementDate + ", " + amount +");");
//			try (CallableStatement cstm = databaseService.prepareCall(sql)) {
//				cstm.setLong(1, participantBankDetailsId);
//				cstm.setString(2, description1);
//
//				cstm.setDate(3, new java.sql.Date(statementDate.getTime()));
//
//				cstm.setDouble(4, amount);
//				cstm.execute();
//				return ResponseEntity.ok().build();
//			} catch (Exception e) {
//				return ResponseEntity.badRequest().body(e.getMessage());
//			}
//		} catch (Exception e) {
// 			e.printStackTrace();
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//
//	}






	@PostMapping("/new")
	public ResponseEntity<?> saveBankStatementNew(@RequestBody BankStatement bankStatement) {
		try {
			BankStatement test = bankStatementService.findByBankStatementId(bankStatement.getBankStatementId());
			if (test != null) {
				throw new Exception("The BankStatement ID already exists");
			}

			bankStatement = bankStatementService.save(bankStatement);
			return ResponseEntity.ok(bankStatement);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveBankStatement(@RequestBody BankStatement bankStatement) {
		try {

			BankStatement test = bankStatementService.findByBankStatementId(bankStatement.getBankStatementId());
			if (test == null) {
				throw new Exception("BankStatement not found");
			}

			bankStatement = bankStatementService.save(bankStatement);
			return ResponseEntity.ok(bankStatement);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@GetMapping("/list-all/{participantBankDetailsId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> getAllBankStatements(	ModelMap modelMap,
												@PathVariable(name = "participantBankDetailsId") Long participantBankDetailsId,
												@PathVariable(name = "firstDay") Date firstDay,
												@PathVariable(name = "lastDay") Date lastDay) {
		try {
			List<BankStatement> result = bankStatementService.getAllBankStatements(participantBankDetailsId, firstDay, lastDay);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/delete/{bankStatementId}")
	public ResponseEntity<?> deleteBankStatement(ModelMap modelMap,
														@PathVariable("bankStatementId") Long bankStatementId) {

		String sql = "CALL ig_db.deleteBankStatement(?);";

		System.out.println ("CALL ig_db.deleteBankStatement(" + bankStatementId +");");
		try {
			Object[] params = {
					bankStatementId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/insert/{participantBankDetailsId}/{description}/{statementNumber}/{statementDate}/{transactionDateFrom}/{transactionDateTo}/{statementPDF}")
	public ResponseEntity<?> insertBankStatement(ModelMap modelMap,
														@PathVariable("participantBankDetailsId") Long participantBankDetailsId,
														@PathVariable("description") String description,
														@PathVariable("statementNumber") String statementNumber,
														@PathVariable("statementDate") Date statementDate,
														@PathVariable("transactionDateFrom") Date transactionDateFrom,
														@PathVariable("transactionDateTo") Date transactionDateTo,
														@PathVariable("statementPDF") Blob statementPDF) {

		Connection conn = null;
		CallableStatement cstm = null;
		try {

			String sql = "CALL ig_db.doInsertBankStatement(?,?,?,?,?,?,?);";
	
			System.out.println ("CALL ig_db.doInsertBankStatement("+participantBankDetailsId+","+description+","+statementNumber+","+statementDate+","+transactionDateFrom+","+transactionDateTo+","+statementPDF+")");
//		try (CallableStatement cstm = databaseService.prepareCall(sql)) {
//			cstm.setLong(1, participantBankDetailsId);
//			cstm.setString(2, description);
//			cstm.setString(3, statementNumber);
//			cstm.setDate(4, new java.sql.Date(statementDate.getTime()));
//			cstm.setDate(5, new java.sql.Date(transactionDateFrom.getTime()));
//			cstm.setDate(6, new java.sql.Date(transactionDateTo.getTime()));
//
////			ps = c.prepareStatement(updateWithPhoto);
////            ps.setString(1, medicalPlanName);
////            Base64Decoder decoder = new Base64Decoder();
////            byte[] imageByte = decoder.decodeBuffer(statementPDF);
////            cstm.setBlob(7, new SerialBlob(imageByte));
//
//			cstm.setBlob(7, statementPDF);
//			cstm.execute();
//			return ResponseEntity.ok().build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
		
			try {
				conn = databaseService.getConnection();
				cstm = conn.prepareCall(sql);
				
				cstm.setLong(1, participantBankDetailsId);
				cstm.setString(2, description);
				cstm.setString(3, statementNumber);
				cstm.setDate(4, new java.sql.Date(statementDate.getTime()));
				cstm.setDate(5, new java.sql.Date(transactionDateFrom.getTime()));
				cstm.setDate(6, new java.sql.Date(transactionDateTo.getTime()));
	
	//			ps = c.prepareStatement(updateWithPhoto);
	//            ps.setString(1, medicalPlanName);
	//            Base64Decoder decoder = new Base64Decoder();
	//            byte[] imageByte = decoder.decodeBuffer(statementPDF);
	//            cstm.setBlob(7, new SerialBlob(imageByte));
	
				cstm.setBlob(7, statementPDF);
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



	@PostMapping("/do-linking")
	public ResponseEntity<?> linkTransactions(@RequestBody Map<String, Object> data) throws JsonProcessingException {
		Connection conn = null;
		CallableStatement cstm = null;
		Long pCount = null;
		String jsonString = null;

//		Individual individual = null;

		try {

			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.doLinkTransactionsToStatement('" + jsonString + "', pCount);";

			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "CALL ig_db.doLinkTransactionsToStatement(?, ?);";

			
			try {
				conn = databaseService.getConnection();
				cstm = conn.prepareCall(sql);
				
				cstm.setString(1, jsonString);
//				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(2, java.sql.Types.BIGINT);

				cstm.execute();
				pCount = cstm.getLong(2);
	
				return ResponseEntity.ok(pCount);
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

}
