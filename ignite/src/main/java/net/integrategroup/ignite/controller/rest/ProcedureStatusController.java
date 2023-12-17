package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.ProcedureStatus;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.ProcedureStatusService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/procedure-status")
public class ProcedureStatusController {


	@Autowired
	ProcedureStatusService procedureStatusService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping()
	public ResponseEntity<?> getProcedureStatus() {
		try {
			List<ProcedureStatus> result = procedureStatusService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveProcedureStatus(@RequestBody ProcedureStatus procedureStatus) {
		try {
			ProcedureStatus test = procedureStatusService.findByProcedureStatusId(procedureStatus.getProcedureStatusId());
			if (test == null) {
				throw new Exception("Competency Level not found");
			}

			procedureStatus = procedureStatusService.save(procedureStatus);

			return ResponseEntity.ok(procedureStatus);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveProcedureStatusNew(@RequestBody ProcedureStatus procedureStatus) {
		try {
			ProcedureStatus test = procedureStatusService.findByProcedureStatusId(procedureStatus.getProcedureStatusId());
			if (test != null) {
				throw new Exception("Competency Level already exists");
			}

			procedureStatus = procedureStatusService.save(procedureStatus);

			return ResponseEntity.ok(procedureStatus);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteProcedureStatus(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long procedureStatusId = mu.getAsLongOrNull(data, "procedureStatusId");
		String sql = "CALL ig_db.deleteProcedureStatus(?);";

		System.out.println ("CALL ig_db.deleteProcedureStatus(" + procedureStatusId+");");
		try {	//**//					
			Object[] params = {		
				procedureStatusId	
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