package net.integrategroup.ignite.controller.rest;

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

import net.integrategroup.ignite.persistence.model.ProjectParticipant;
import net.integrategroup.ignite.persistence.model.RemunerationRateUpline;
import net.integrategroup.ignite.persistence.model.VPPTree;
import net.integrategroup.ignite.persistence.model.VProjectParticipant;
import net.integrategroup.ignite.persistence.model.VProjectParticipantList;
import net.integrategroup.ignite.persistence.model.VProjectParticipantPayerBen;
import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.IndividualService;
import net.integrategroup.ignite.persistence.service.ProjectParticipantService;
import net.integrategroup.ignite.persistence.service.ProjectService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/project-participant")
public class ProjectParticipantController {


	@Autowired
	DatabaseService databaseService;

	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectParticipantService projectParticipantService;

	@Autowired
	IndividualService individualService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping("/list/{projectId}")
	public ResponseEntity<?> getProjectParticipantList(@PathVariable("projectId") Long projectId, ModelMap modelMap) {

		try {
			List<VPPTree> result = projectParticipantService.findAllForProjectId(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/list/all")
	public ResponseEntity<?> getAllProjectParticipants() {
		try {
			List<ProjectParticipant> result = projectParticipantService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/contracted-participant-list/{projectId}")
	public ResponseEntity<?> getContractedParticipantList(@PathVariable("projectId") Long projectId,
			ModelMap modelMap) {
		try {
			List<ProjectParticipant> result = projectParticipantService.findContractedParticipantList(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/children/{projectParticipantId}")
	public ResponseEntity<?> getParticipantChildrenList(@PathVariable("projectParticipantId") Long projectParticipantId,
			ModelMap modelMap) {
		try {
			List<VPPTree> result = projectParticipantService
					.getParticipantChildrenList(projectParticipantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@GetMapping("/children/{projectParticipantId}")
//	public ResponseEntity<?> getParticipantChildrenList(@PathVariable("projectParticipantId") Long projectParticipantId,
//			ModelMap modelMap) {
//		try {
//			List<VProjectParticipant> result = projectParticipantService
//					.getParticipantChildrenList(projectParticipantId);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteProjectParticipant(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long projectParticipantId = mu.getAsLongOrNull(data, "projectParticipantIdContracted");
		String sql = "CALL ig_db.deleteProjectParticipant(?);";

		System.out.println ("CALL ig_db.deleteProjectParticipant(" + projectParticipantId +");");
		try {	//**//					
			Object[] params = {		
				projectParticipantId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
	}

	@PostMapping()
	public ResponseEntity<?> saveProjectParticipant(@RequestBody ProjectParticipant projectParticipant) {
		try {

			projectParticipant = projectParticipantService.save(projectParticipant);

			return ResponseEntity.ok(projectParticipant);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/view/{projectId}")
	public ResponseEntity<?> findAllInPPViewForProjectId(@PathVariable("projectId") Long projectId, ModelMap modelMap) {
		try {
			List<VProjectParticipantPayerBen> result = projectParticipantService.findAllInPPViewForProjectId(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/for-participant/{participantId}")
	public ResponseEntity<?> findAllPPForParticipantId(	@PathVariable("participantId") Long participantId,
														ModelMap modelMap) {

		try {
			List<VProjectParticipant> result = projectParticipantService.findAllPPForParticipantId(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}






	@GetMapping("/for-participant/remuneration-rate-upline-for-date/{participantId}/{projectParticipantIdAboveMe}/{activityDate}")
	public ResponseEntity<?> findAllPPForParticipantId(	@PathVariable("participantId") Long participantId,
														@PathVariable("projectParticipantIdAboveMe") Long projectParticipantIdAboveMe,
														@PathVariable("activityDate") Date activityDate,
														ModelMap modelMap) {

		try {

			RemunerationRateUpline result = projectParticipantService.getRemunerationRateUplineForDate(participantId,projectParticipantIdAboveMe,activityDate);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/sd-role/{projectParticipantId}")
	public ResponseEntity<?> findSdAndRolesForProjectParticipantId(	@PathVariable("projectParticipantId") Long projectParticipantId,
														ModelMap modelMap) {

		try {
			List<VProjectParticipantSdRoles> result = projectParticipantService.findSdAndRolesForProjectParticipantId(projectParticipantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project-sd-role/{participantId}")
	public ResponseEntity<?> findProjectSdAndRolesForParticipant(@PathVariable("participantId") Long participantId,
																ModelMap modelMap) {

		try {
			List<VProjectParticipantSdRoles> result = projectParticipantService.findProjectSdAndRolesForParticipant(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/relationships/{projectId}")
	public ResponseEntity<?> getProjectRelationshipList(@PathVariable("projectId") Long projectId, ModelMap modelMap) {

		try {
			List<VPPTree> result = projectParticipantService.getProjectRelationshipList(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project-view/{participantIdContracting}/{participantIdContracted}")
	public ResponseEntity<?> findProjectsForParticipantIds(
										@PathVariable("participantIdContracting") Long participantIdContracting,
										@PathVariable("participantIdContracted") Long participantIdContracted,
										ModelMap modelMap) {
		try {
			List<VProjectParticipantPayerBen> result = projectParticipantService.findProjectsForParticipantIds(participantIdContracting,participantIdContracted);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list-only-my-projects-all/{participantId}")
	public ResponseEntity<?> findOnlyMyProjectsAll(ModelMap modelMap,
			@PathVariable(name = "participantId", required = false) Long participantId) {
		try {

			List<VProjectParticipantList> result = projectParticipantService.findOnlyMyProjectsAll(participantId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}	
	
	
}
