package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
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

import net.integrategroup.ignite.persistence.model.PpSdRoleStage;
import net.integrategroup.ignite.persistence.model.VPpSdRoleStage;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.PpSdRoleStageService;
import net.integrategroup.ignite.persistence.service.VPpSdRoleStageService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/pp-sd-role-stage")
public class PpSdRoleStageController {

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	PpSdRoleStageService ppSdRoleStageService;

	@Autowired
	VPpSdRoleStageService vPpSdRoleStageService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping("/ppsd-role/{id}")
	public ResponseEntity<?> findPpSdRoleStageByPpSdRid(@PathVariable("id") Long projectParticipantSdRoleId) {
		try {
			List<PpSdRoleStage> result = ppSdRoleStageService
					.findPpSdRoleStageByPpSdRid(projectParticipantSdRoleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/vppsd-role/{id}")
	public ResponseEntity<?> findVPpSdRoleStageByPpSdRid(@PathVariable("id") Long projectParticipantSdRoleId) {
		try {
			List<VPpSdRoleStage> result = vPpSdRoleStageService
					.findVPpSdRoleStageByPpSdRid(projectParticipantSdRoleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/vppsd-stage/{id}")
	public ResponseEntity<?> findVPpSdRoleStageByProjectSdId(@PathVariable("id") Long projectSdId) {
		try {
			List<VPpSdRoleStage> result = vPpSdRoleStageService
					.findVPpSdRoleStageByProjectSdId(projectSdId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> savePpSdRoleStage(@RequestBody PpSdRoleStage ppSdRoleStage) {
		try {

			PpSdRoleStage test = ppSdRoleStageService.findByPpSdRoleStageId(ppSdRoleStage.getPpSdRoleStageId());
			if (test == null) {
				throw new Exception("The link not found");
			}

			ppSdRoleStage = ppSdRoleStageService.save(ppSdRoleStage);
			return ResponseEntity.ok(ppSdRoleStage);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> savePpSdRoleStageNew(@RequestBody PpSdRoleStage ppSdRoleStage) {
		try {

			PpSdRoleStage test = ppSdRoleStageService.findByPpSdRoleStageId(ppSdRoleStage.getPpSdRoleStageId());
			if (test != null) {
				throw new Exception("The link already exists");
			}

			ppSdRoleStage = ppSdRoleStageService.save(ppSdRoleStage);
			return ResponseEntity.ok(ppSdRoleStage);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/delete")
	public ResponseEntity<?> deletePpSdRoleStage(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long ppSdRoleStageId = mu.getAsLongOrNull(data, "ppSdRoleStageId");
		String sql = "CALL ig_db.deletePpSdRoleStage(?);";

		System.out.println ("CALL ig_db.deletePpSdRoleStage(" + ppSdRoleStageId+");");
		try {	//**//					
			Object[] params = {		
				ppSdRoleStageId	
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
