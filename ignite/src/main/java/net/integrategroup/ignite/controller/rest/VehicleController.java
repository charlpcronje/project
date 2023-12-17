package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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

import net.integrategroup.ignite.model.VehicleUserDto;
import net.integrategroup.ignite.persistence.model.VVehicle;
import net.integrategroup.ignite.persistence.model.Vehicle;
import net.integrategroup.ignite.persistence.model.VehicleUser;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.VehicleService;
import net.integrategroup.ignite.persistence.service.VehicleUserService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/vehicle")
public class VehicleController {

	@Autowired
	VehicleService vehicleService;

	@Autowired
	VehicleUserService vehicleUserService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getAllVehicles() {
		try {
			List<Vehicle> result = vehicleService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findByVehicleId(@PathVariable("id") Long vehicleId) {
		try {
			VVehicle result = vehicleService.getVehicleDetail(vehicleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveVehicle(@RequestBody Vehicle vehicle) {
		try {

			Vehicle test = vehicleService.findByVehicleId(vehicle.getVehicleId());
			if (test == null) {
				throw new Exception("Vehicle not found");
			}

			vehicle = vehicleService.save(vehicle);
			return ResponseEntity.ok(vehicle);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@PostMapping("/new")
	public ResponseEntity<?> saveNewVehicle(@RequestBody Map<String, Object> data) throws JsonProcessingException {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		Long vehicleId = null;
		Vehicle vehicle = null;
		final String PASSWORD_KEY = "pass";

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
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveNewVehicle('" + jsonString + "', '" + securityUtils.getUsername()
					+ "', vehicleId"
					+ ");";
			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "CALL ig_db.saveNewVehicle(?, ?, ?);";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);

				cstm.execute();
				vehicleId = cstm.getLong(3);

				if (vehicleId != null) {
					vehicle = vehicleService.findByVehicleId(vehicleId);
				}
				return ResponseEntity.ok(vehicle);

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
	public ResponseEntity<?> deleteVehicle(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long vehicleId = mu.getAsLongOrNull(data, "vehicleId");
		String sql = "CALL ig_db.deleteVehicle(?);";

		System.out.println ("CALL ig_db.deleteVehicle(" + vehicleId +");");
		try {	//**//					
			Object[] params = {		
				vehicleId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/participant/{id}")
	public ResponseEntity<?> findByParticipantId(@PathVariable("id") Long participantId) {
		try {
			List<Vehicle> result = vehicleService.findVehicleByParticipantId(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/user/{vehicleId}")
	public ResponseEntity<?> findVehicleUserByVehicleId(@PathVariable("vehicleId") Long vehicleId) {
		try {
			List<VehicleUserDto> result = vehicleUserService.findVehicleUserByVehicleId(vehicleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/user/new")
	public ResponseEntity<?> saveVehicleUserNew(@RequestBody VehicleUser vehicleUser) {
		try {
			VehicleUser test = vehicleUserService.findByVehicleUserId(vehicleUser.getVehicleUserId());
			if (test != null) {
				throw new Exception("The Vehicle User already exists");
			}

			vehicleUser = vehicleUserService.save(vehicleUser);
			return ResponseEntity.ok(vehicleUser);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	@PostMapping("/user")
	public ResponseEntity<?> saveVehicleUser(@RequestBody VehicleUser vehicleUser) {
		try {
			VehicleUser test = vehicleUserService.findByVehicleUserId(vehicleUser.getVehicleUserId());
			if (test == null) {
				throw new Exception("The Asset User does not exist");
			}

			vehicleUser = vehicleUserService.save(vehicleUser);
			return ResponseEntity.ok(vehicleUser);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	@PostMapping("/user/delete")
	public ResponseEntity<?> deleteVehicleUser(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long vehicleUserId = mu.getAsLongOrNull(data, "vehicleUserId");
		String sql = "CALL ig_db.deleteVehicleUser(?);";

		System.out.println ("CALL ig_db.deleteVehicleUser(" + vehicleUserId +");");
		try {	//**//					
			Object[] params = {		
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



}