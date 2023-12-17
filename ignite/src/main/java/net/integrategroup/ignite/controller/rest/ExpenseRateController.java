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

import net.integrategroup.ignite.persistence.model.VExpenseRate;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ExpenseRateService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/expense-rate")
public class ExpenseRateController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ExpenseRateService expenseRateService;

	@Autowired
	SecurityUtils securityUtils;

	@PostMapping("/delete")
	public ResponseEntity<?> deleteExpenseRate(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long expenseRateId = mu.getAsLongOrNull(data, "expenseRateId");
		String sql = "CALL ig_db.deleteExpenseRate(?);";

		System.out.println ("CALL ig_db.deleteExpenseRate(" + expenseRateId +");");
		try {
			Object[] params = {
					expenseRateId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/save-expense-rate")
	public ResponseEntity<?> saveExpenseRateUpline(@RequestBody Map<String, Object> data) {

		Connection conn = null;
		CallableStatement cstm = null;

		MapUtils mu = new MapUtils();
		Long expenseRateId = mu.getAsLongOrNull(data, "expenseRateId"); 				//1
		Long recoverableExpenseId = mu.getAsLongOrNull(data, "recoverableExpenseId");						//5
		Double expenseRatePerUnit= mu.getAsDoubleOrNull(data, "expenseRatePerUnit");										//6
		Double expenseHandlingPerc= mu.getAsDoubleOrNull(data, "expenseHandlingPerc");						//7
		Double maxExpenseAmtPerUnit= mu.getAsDoubleOrNull(data, "maxExpenseAmtPerUnit");					//8
		String description= mu.getAsStringOrNull(data, "description");										//9
		Date startDate= mu.getAsDateOrNull(data, "startDate"); 												//10
		Date endDate= mu.getAsDateOrNull(data, "endDate");													//11
		String userName = securityUtils.getUsername();														//12

		try {
			String sql = "CALL ig_db.saveExpenseRate(?,?,?,?,?,?,?,?,?);";

			System.out.println ("CALL ig_db.saveExpenseRate("
																+ expenseRateId + ","
																+ recoverableExpenseId + ","
																+ expenseRatePerUnit + ",'"
																+ expenseHandlingPerc + ","
																+ maxExpenseAmtPerUnit + ","
																+ description + "',"
																+ startDate + ","
																+ endDate + ",'"
																+ userName +"');");
			try {
				conn = databaseService.getConnection();
				cstm = conn.prepareCall(sql);
				if (expenseRateId == null) {
					cstm.setNull(1, Types.BIGINT);
				} else {
					cstm.setLong(1, expenseRateId);
				}
				if (recoverableExpenseId == null) {
					cstm.setNull(2, Types.BIGINT);
				} else {
					cstm.setLong(2, recoverableExpenseId);
				}
				if (expenseRatePerUnit == null) {
					cstm.setNull(3, Types.DECIMAL);
				} else {
					cstm.setDouble(3, expenseRatePerUnit);
				}
				if (expenseHandlingPerc == null) {
					cstm.setNull(4, Types.DECIMAL);
				} else {
					cstm.setDouble(4, expenseHandlingPerc);
				}
				if (maxExpenseAmtPerUnit == null) {
					cstm.setNull(5, Types.DECIMAL);
				} else {
					cstm.setDouble(5, maxExpenseAmtPerUnit);
				}
				if (description == null) {
					cstm.setNull(6, Types.VARCHAR);
				} else {
					cstm.setString(6, description);
				}
				cstm.setDate(7, new java.sql.Date(startDate.getTime()));
	
				if (endDate == null) {
					cstm.setNull(8, Types.DATE);
				} else {
					cstm.setDate(8, new java.sql.Date(endDate.getTime()));
				}
	
				cstm.setString(9, securityUtils.getUsername());
	
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


	@GetMapping("/expense-rates/{recoverableExpenseId}")
	public ResponseEntity<?> getExpenseRates(	ModelMap modelMap,
												@PathVariable("recoverableExpenseId") Long recoverableExpenseId) {
		try {
			List<VExpenseRate> result = expenseRateService.getExpenseRates(recoverableExpenseId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/expense-rates/current/{recoverableExpenseId}")
	public ResponseEntity<?> getExpenseRatesCurrent(ModelMap modelMap,
													@PathVariable("recoverableExpenseId") Long recoverableExpenseId) {
		try {
			List<VExpenseRate> result = expenseRateService.getExpenseRatesCurrent(recoverableExpenseId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
