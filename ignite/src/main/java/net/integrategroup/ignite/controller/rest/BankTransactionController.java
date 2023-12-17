package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.util.Date;
//import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.BankTransaction;
import net.integrategroup.ignite.persistence.model.VBankTransaction;
import net.integrategroup.ignite.persistence.service.BankTransactionService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/bank-transaction")
public class BankTransactionController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	BankTransactionService bankTransactionService;

	@Autowired
	SecurityUtils securityUtils;


//	@PostMapping("/insert-bank-transaction")   ///{assignmentTypeId}/{assignmentId}")
//	public ResponseEntity<?> doInsertBankTransaction(@RequestBody Map<String, Object> data) {
//		try {
//			MapUtils mu = new MapUtils();
//			Long   participantBankDetailsId = mu.getAsLongOrNull(data, "participantBankDetailsId");
//			String description1 = mu.getAsStringOrNull(data, "description1");
//			Date   transactionDate = mu.getAsDateOrNull(data, "transactionDate");
//			Double amount = mu.getAsDoubleOrNull(data, "amount");
//			String sql = "CALL ig_db.doInsertBankTransaction(?, ?, ?, ?);";
//
//	//		System.out.println ("CALL ig_db.doInsertBankTransaction(" + participantBankDetailsId + ", " + description1 + ", " + transactionDate + ", " + amount +");");
//			try (CallableStatement cstm = databaseService.prepareCall(sql)) {
//				cstm.setLong(1, participantBankDetailsId);
//				cstm.setString(2, description1);
//
//				cstm.setDate(3, new java.sql.Date(transactionDate.getTime()));
//
//				cstm.setDouble(4, amount);
//				cstm.execute();
//				return ResponseEntity.ok().build();
//			} catch (Exception e) {
//				return ResponseEntity.badRequest().body(e.getMessage());
//			}
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//
//	}






	@PostMapping("/new")
	public ResponseEntity<?> saveBankTransactionNew(@RequestBody BankTransaction bankTransaction) {
		try {
			BankTransaction test = bankTransactionService.findByBankTransactionId(bankTransaction.getBankTransactionId());
			if (test != null) {
				throw new Exception("The BankTransaction ID already exists");
			}

			bankTransaction = bankTransactionService.save(bankTransaction);
			return ResponseEntity.ok(bankTransaction);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveBankTransaction(@RequestBody BankTransaction bankTransaction) {
		try {

			BankTransaction test = bankTransactionService.findByBankTransactionId(bankTransaction.getBankTransactionId());
			if (test == null) {
				throw new Exception("BankTransaction not found");
			}

			bankTransaction = bankTransactionService.save(bankTransaction);
			return ResponseEntity.ok(bankTransaction);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	// /rest/ignite/v1/bank-transaction/list-all/

	@GetMapping("/list-all/{participantBankDetailsId}")
	public ResponseEntity<?> getAllBankTransactions(	ModelMap modelMap,
												@PathVariable(name = "participantBankDetailsId") Long participantBankDetailsId) {
		try {
			List<VBankTransaction> result = bankTransactionService.getAllBankTransactions(participantBankDetailsId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	// /rest/ignite/v1/bank-transaction/list-statement/

	@GetMapping("/list-statement/{bankStatementId}")
	public ResponseEntity<?> getAllBankTransactionsStatement(	ModelMap modelMap,
												@PathVariable(name = "bankStatementId") Long bankStatementId) {
		try {
			List<VBankTransaction> result = bankTransactionService.getAllBankTransactionsStatement(bankStatementId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	// /rest/ignite/v1/bank-transaction/list-participant/
	@GetMapping("/list-participant/{participantBankDetailsId}/{firstDay}/{lastDay}")
	public ResponseEntity<?> getAllBankTransactionsDates(	ModelMap modelMap,
												@PathVariable(name = "participantBankDetailsId") Long participantBankDetailsId,
												@PathVariable(name = "firstDay") Long firstDay,
												@PathVariable(name = "lastDay") Long lastDay) {
		try {
			Date fd = new Date(firstDay);
			Date ld = new Date(lastDay);

			List<VBankTransaction> result = bankTransactionService.getAllBankTransactionsDates(participantBankDetailsId, fd, ld);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



//	@PostMapping("/delete/{bankTransactionId}")
//	public ResponseEntity<?> deleteBankTransaction(@RequestBody Map<String, Object> data) {
//
//		MapUtils mu = new MapUtils();
//		String bankTransactionId = mu.getAsLongOrNull(data, "bankTransactionId");
//		String sql = "CALL ig_db.deleteBankTransaction(?);";
//
//		System.out.println ("CALL ig_db.deleteBankTransaction(" + bankTransactionId +");");
//		try (CallableStatement cstm = databaseService.prepareCall(sql)) {
//			cstm.setLong(1, bankTransactionId);
//			cstm.execute();
//			return ResponseEntity.ok().build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@PostMapping("/delete/{bankTransactionId}")
	public ResponseEntity<?> deleteBankTransaction(ModelMap modelMap,
														@PathVariable("bankTransactionId") Long bankTransactionId) {

		String sql = "CALL ig_db.deleteBankTransaction(?);";

		System.out.println ("CALL ig_db.deleteBankTransaction(" + bankTransactionId +");");

		try {	//**//					
			Object[] params = {		
				bankTransactionId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/last-bank-transaction/{participantBankDetailsId}")
	public ResponseEntity<?> getLastBankTransaction(@PathVariable("participantBankDetailsId") Long participantBankDetailsId, ModelMap modelMap) {
		try {

			BankTransaction result = bankTransactionService.getLastBankTransaction(participantBankDetailsId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}








}
