package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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

import net.integrategroup.ignite.persistence.model.VServiceDiscipline;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ServiceDisciplineService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/service-discipline")
public class ServiceDisciplineController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ServiceDisciplineService serviceDisciplineService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping("/all")
	public ResponseEntity<?> findAllSss(ModelMap modelMap) {
		try {
			List<VServiceDiscipline> result = serviceDisciplineService.findAllSss();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/level0")
	public ResponseEntity<?> findLevel0List(ModelMap modelMap) {
		try {
			List<VServiceDiscipline> result = serviceDisciplineService.findLevel0List();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/children/{serviceDisciplineId}")
	public ResponseEntity<?> findChildren(ModelMap modelMap, @PathVariable(name = "serviceDisciplineId") Long serviceDisciplineId) {
		try {
			List<VServiceDiscipline> result = serviceDisciplineService.findChildren(serviceDisciplineId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/expand-children/{serviceDisciplineArray}")
	public ResponseEntity<?> expandChildren(ModelMap modelMap, @PathVariable(name = "serviceDisciplineArray") String serviceDisciplineArray) {
		try {
			List<VServiceDiscipline> result = serviceDisciplineService.expandChildren(serviceDisciplineArray);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping("/new")
	public ResponseEntity<?> saveServiceDisciplineNew(@RequestBody Map<String, Object> data) throws JsonProcessingException {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		Long serviceDisciplineId = null;

		try {

			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveServiceDisciplineNew('" + jsonString + "', '" + securityUtils.getUsername()
					+ "', serviceDisciplineId"
					+ ");";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "CALL ig_db.saveServiceDisciplineNew(?, ?, ?);";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);

				cstm.execute();
				serviceDisciplineId = cstm.getLong(3);
		
				return ResponseEntity.ok(serviceDisciplineId);
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

	@PostMapping()
	public ResponseEntity<?> saveServiceDiscipline(@RequestBody Map<String, Object> data) throws JsonProcessingException {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		Long serviceDisciplineId = null;

		try {

			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveServiceDisciplineUpdate('" + jsonString + "', '" + securityUtils.getUsername()
					+ "', serviceDisciplineId"
					+ ");";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "CALL ig_db.saveServiceDisciplineUpdate(?, ?, ?);";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);

				cstm.execute();
				serviceDisciplineId = cstm.getLong(3);

				return ResponseEntity.ok(serviceDisciplineId);
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
	

	@PostMapping("/delete")
	public ResponseEntity<?> deleteServiceDiscipline(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long serviceDisciplineId = mu.getAsLongOrNull(data, "serviceDisciplineId");
		String sql = "CALL ig_db.deleteServiceDiscipline(?);";

		System.out.println ("CALL ig_db.deleteServiceDiscipline(" + serviceDisciplineId+");");
		try {	//**//					
			Object[] params = {		
				serviceDisciplineId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Hier haal ons net een rekord van die db
	@GetMapping("/by-code/{serviceDisciplineId}")
	public ResponseEntity<?> findByVServiceDisciplineId(@PathVariable("serviceDisciplineId") Long serviceDisciplineId,
			ModelMap modelMap) {
		try {
			VServiceDiscipline result = serviceDisciplineService.findByVServiceDisciplineId(serviceDisciplineId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}
