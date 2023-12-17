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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.ResourceRemuneration;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ResourceRemunerationService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/resource-remuneration")
public class ResourceRemunerationController {

	@Autowired
	ResourceRemunerationService resourceRemunerationService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping("/{id}")
	public ResponseEntity<?> getResourceRemunerations(@PathVariable("id") Long nonProjectRelatedAgreementId) {
		try {
			List<ResourceRemuneration> result = resourceRemunerationService
					.findAllForHumanResource(nonProjectRelatedAgreementId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping()
	public ResponseEntity<?> saveResourceRemuneration(@RequestBody ResourceRemuneration resourceRemuneration) {
		try {
			ResourceRemuneration test = resourceRemunerationService.findByResourceRemunerationId(resourceRemuneration.getResourceRemunerationId());
			if (test == null) {
				throw new Exception("Non Project Remuneration not found");
			}
			resourceRemuneration = resourceRemunerationService.save(resourceRemuneration);

			return ResponseEntity.ok(resourceRemuneration);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("new")
	public ResponseEntity<?> saveResourceRemunerationNew(@RequestBody ResourceRemuneration resourceRemuneration) {
		try {
			ResourceRemuneration test = resourceRemunerationService.findByResourceRemunerationId(resourceRemuneration.getResourceRemunerationId());
			if (test != null) {
				throw new Exception("The Non Project Remuneration already exists");
			}
			resourceRemuneration = resourceRemunerationService.save(resourceRemuneration);

			return ResponseEntity.ok(resourceRemuneration);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteResourceRemuneration(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long resourceRemunerationId = mu.getAsLongOrNull(data, "resourceRemunerationId");

		String sql = "CALL ig_db.deleteResourceRemuneration(?);";

		System.out.println ("CALL ig_db.deleteResourceRemuneration(" + resourceRemunerationId +");");
		try {	//**//					
			Object[] params = {		
					resourceRemunerationId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}




	@PostMapping("/save-sp")
	public ResponseEntity<?> saveResourceRemuneration(@RequestBody Map<String, Object> data) {


		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		MapUtils mu = new MapUtils();
		Long   resourceRemunerationId = mu.getAsLongOrNull(data, "resourceRemunerationId"); 				//1
		Long   nonProjectRelatedAgreementId= mu.getAsLongOrNull(data, "nonProjectRelatedAgreementId");	//2
		Long resourceRemunTypeId= mu.getAsLongOrNull(data, "resourceRemunTypeId");				//3
		String description= mu.getAsStringOrNull(data, "description");									//4
		Date   startDate= mu.getAsDateOrNull(data, "startDate"); 											//5
		Date   endDate= mu.getAsDateOrNull(data, "endDate");												//6
		Double amount= mu.getAsDoubleOrNull(data, "amount");										    //7
		String allowExpenseDeductions= mu.getAsStringOrNull(data, "allowExpenseDeductions");			//8							//8
		String userName = securityUtils.getUsername();													//9

		try {
			String sql = "CALL ig_db.saveResourceRemuneration(?,?,?,?,?,?,?,?,?);";

			System.out.println ("CALL ig_db.saveResourceRemuneration("
																+ resourceRemunerationId + ","
																+ nonProjectRelatedAgreementId + ","
																+ resourceRemunTypeId + "',"
																+ description + "',"
																+ startDate + ","
																+ endDate + ",'"
																+ amount + ",'"
																+ allowExpenseDeductions + ",'"
																+ userName +"');");

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				if (resourceRemunerationId == null) {
					cstm.setNull(1, Types.BIGINT);
				} else {
					cstm.setLong(1, resourceRemunerationId);
				}
	
				cstm.setLong(2, nonProjectRelatedAgreementId);
	
				cstm.setLong(3, resourceRemunTypeId);
	
				if (description == null) {
					cstm.setNull(4, Types.VARCHAR);
				} else {
					cstm.setString(4, description);
				}
	
				cstm.setDate(5, new java.sql.Date(startDate.getTime()));
	
				if (endDate == null) {
					cstm.setNull(6, Types.DATE);
				} else {
					cstm.setDate(6, new java.sql.Date(endDate.getTime()));
				}
	
				if (amount == null) {
					cstm.setNull(7, Types.DECIMAL);
				} else {
					cstm.setDouble(7, amount);
				}
	
				if (allowExpenseDeductions == null) {
					cstm.setNull(8, Types.VARCHAR);
				} else {
					cstm.setString(8, allowExpenseDeductions);
				}
	
	
				cstm.setString(9, securityUtils.getUsername());
	
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



}