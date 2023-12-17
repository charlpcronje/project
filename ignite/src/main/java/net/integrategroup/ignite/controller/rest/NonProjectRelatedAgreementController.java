package net.integrategroup.ignite.controller.rest;

import java.sql.CallableStatement;
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

import net.integrategroup.ignite.persistence.model.NonProjectRelatedAgreement;
import net.integrategroup.ignite.persistence.model.VHumanResourceUnionList;
import net.integrategroup.ignite.persistence.model.VNonProjectRelatedAgreement;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.NonProjectRelatedAgreementService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/non-project-related-agreement")
public class NonProjectRelatedAgreementController {

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	NonProjectRelatedAgreementService nonProjectRelatedAgreementService;


	@Autowired
	DatabaseService databaseService;

	@GetMapping("/participant/{id}")
	public ResponseEntity<?> getNonProjectRelatedAgreementForParticipant(@PathVariable("id") Long participantId) {
		try {
			List<VNonProjectRelatedAgreement> result = nonProjectRelatedAgreementService
					.getNonProjectRelatedAgreementForParticipant(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping()
	public ResponseEntity<?> saveNonProjectRelatedAgreement(@RequestBody NonProjectRelatedAgreement nonProjectRelatedAgreement) {
		try {

			NonProjectRelatedAgreement test = nonProjectRelatedAgreementService.findByNonProjectRelatedAgreementId(nonProjectRelatedAgreement.getNonProjectRelatedAgreementId());
			if (test == null) {
				throw new Exception("NonProjectRelatedAgreement not found");
			}

			nonProjectRelatedAgreement = nonProjectRelatedAgreementService.save(nonProjectRelatedAgreement);
			return ResponseEntity.ok(nonProjectRelatedAgreement);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")
	public ResponseEntity<?> saveNonProjectRelatedAgreementNew(@RequestBody NonProjectRelatedAgreement nonProjectRelatedAgreement) {
		try {

			NonProjectRelatedAgreement test = nonProjectRelatedAgreementService.findByNonProjectRelatedAgreementId(nonProjectRelatedAgreement.getNonProjectRelatedAgreementId());
			if (test != null) {
				throw new Exception("NonProjectRelatedAgreement already exists");
			}

			nonProjectRelatedAgreement = nonProjectRelatedAgreementService.save(nonProjectRelatedAgreement);
			return ResponseEntity.ok(nonProjectRelatedAgreement);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}





	@PostMapping("/delete")
	public ResponseEntity<?> deleteNonProjectRelatedAgreement(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long nonProjectRelatedAgreementId = mu.getAsLongOrNull(data, "nonProjectRelatedAgreementId");
		String sql = "CALL ig_db.deleteNonProjectRelatedAgreement(?);";

		System.out.println ("CALL ig_db.deleteNonProjectRelatedAgreement(" + nonProjectRelatedAgreementId+");");
		try {	//**//					
			Object[] params = {		
					nonProjectRelatedAgreementId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}


	@GetMapping("/all-resources-list/{participantId}")
	public ResponseEntity<?> findHrByParticipantId(ModelMap modelMap, @PathVariable("participantId") Long participantId) {
		try {
			List<VHumanResourceUnionList> result = nonProjectRelatedAgreementService.findHrByParticipantId(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



//	@GetMapping("/{participantId}")
//	public ResponseEntity<?> getHumanResources(ModelMap modelMap, @PathVariable(name = "participantId") Long participantId) {
//		try {
//			List<HumanResource> result = humanResourceService.findHumanResources(participantId);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@GetMapping("/resource-of/{participantId}")
	public ResponseEntity<?> getWhoParticipantIsAResourceOf(@PathVariable("participantId") Long participantId) {
		try {
			List<VNonProjectRelatedAgreement> result = nonProjectRelatedAgreementService
					.getWhoParticipantIsAResourceOf(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}
