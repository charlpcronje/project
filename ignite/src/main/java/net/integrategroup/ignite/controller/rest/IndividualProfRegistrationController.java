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

import net.integrategroup.ignite.persistence.model.IndividualProfRegistration;
import net.integrategroup.ignite.persistence.model.VIndividualProfRegistration;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.IndividualProfRegistrationService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;



/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-16 19:51:56 ******** ***** **/


@RestController
@RequestMapping("/rest/ignite/v1/individual-prof-registration")

public class IndividualProfRegistrationController {

	@Autowired
	IndividualProfRegistrationService individualProfRegistrationService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping("/find-all")          //All records in the IndividualProfRegistration Table
	public ResponseEntity<?> findAll() {
		try {
			List<IndividualProfRegistration> result = individualProfRegistrationService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/by-individual-prof-registration-id/{individualProfRegistrationId}")   //Find the record by the PrimaryKey
	public ResponseEntity<?> getByIndividualProfRegistrationId(@PathVariable("individualProfRegistrationId") Long individualProfRegistrationId) {
		try {
			IndividualProfRegistration result = individualProfRegistrationService.getByIndividualProfRegistrationId(individualProfRegistrationId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()       //Save (update)
	public ResponseEntity<?> saveIndividualProfRegistration(@RequestBody IndividualProfRegistration individualProfRegistration) {
		try {
			IndividualProfRegistration test = individualProfRegistrationService.getByIndividualProfRegistrationId(individualProfRegistration.getIndividualProfRegistrationId());
			if (test == null) {
				throw new Exception("IndividualProfRegistration not found");
			}
			individualProfRegistration = individualProfRegistrationService.save(individualProfRegistration);
			return ResponseEntity.ok(individualProfRegistration);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")   //Save (new)
	public ResponseEntity<?> saveIndividualProfRegistrationNew(@RequestBody IndividualProfRegistration individualProfRegistration) {
		try {
			IndividualProfRegistration test = individualProfRegistrationService.getByIndividualProfRegistrationId(individualProfRegistration.getIndividualProfRegistrationId());
			if (test != null) {
				throw new Exception("The IndividualProfRegistration already exists");
			}
			individualProfRegistration = individualProfRegistrationService.save(individualProfRegistration);
			return ResponseEntity.ok(individualProfRegistration);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@GetMapping("/by-view-individual-prof-registration-id/{individualProfRegistrationId}")   //One record from View
//	public ResponseEntity<?> getByVIndividualProfRegistrationId(@PathVariable("individualProfRegistrationId") Long individualProfRegistrationId) {
//		try {
//			VIndividualProfRegistration result = individualProfRegistrationService.getByVIndividualProfRegistrationId(individualProfRegistrationId);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}


	@PostMapping("/delete")   //Delete a record, and insert into AuditTrial table
	public ResponseEntity<?> deleteIndividualProfRegistration(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long individualProfRegistrationId = mu.getAsLongOrNull(data, "individualProfRegistrationId");
		String sql = "CALL ig_db.deleteIndividualProfRegistration(?, ?);";

		String exampleSql = "CALL ig_db.deleteIndividualProfRegistration(" + individualProfRegistrationId + ", '"
			+ securityUtils.getUsername() + "');";
		System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

		try {	//**//					
			Object[] params = {		
					individualProfRegistrationId,
					securityUtils.getUsername()
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@GetMapping("/find-all-in-view")                                            //All in view
//	public ResponseEntity<?> findListAllInView()  {
//		try {
//			List<VIndividualProfRegistration> result = individualProfRegistrationService.findListAllInView();
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

//	@GetMapping("/list-view-individual-prof-registration-for-XXX")                                       //Find List from View without parameter
//	public ResponseEntity<?> findListIndividualProfRegistrationForXXX()  {
//		try {
//			List<VIndividualProfRegistration> result = individualProfRegistrationService.findListVIndividualProfRegistrationForXXX();
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@GetMapping("/list-view-individual-prof-registration-for-participant/{participantId}")                           //Find List from View that needs parameter
	public ResponseEntity<?> findListVIndividualProfRegistrationForParticipant(@PathVariable("participantId") Long participantId,	ModelMap modelMap)  {
		try {
			List<VIndividualProfRegistration> result = individualProfRegistrationService.findListVIndividualProfRegistrationForParticipant(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@GetMapping("/list-view-individual-prof-registration-for-XXX/{paramId}/{paramName}/{paramName2}")   //Find List from View that needs multiple parameters
//	public ResponseEntity<?> findListVIndividualProfRegistrationForXXX(@PathVariable("paramId") Long paramId,
//		                                        @PathVariable("paramName") Long paramName,
//		                                        @PathVariable("paramName") Long paramName) {
//		try {
//			List<VIndividualProfRegistration> result = individualProfRegistrationService.findListVIndividualProfRegistrationForXXX(paramId, paramName, paramName2);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

//	@GetMapping("/list-view-individual-prof-registration-for-XXX/{paramId}/{firstDay}/{lastDay}")   //Find List from View that needs date parameters   (use variable like this:  var dateParam = getMsFromDatePicker("theStartDate");)
//	public ResponseEntity<?> findListIndividualProfRegistrationForXXX(ModelMap modelMap,
//		                                        @PathVariable("paramId") Long paramId,
//		                                        @PathVariable(name = "firstDay") Long firstDay,
//		                                        @PathVariable(name = "lastDay") Long lastDay) {
//		try {
//			Date fd = new Date(firstDay);     // Dates sent to db
//			Date ld = new Date(lastDay);
//			List<VIndividualProfRegistration> result = individualProfRegistrationService.findListVIndividualProfRegistrationForXXX(paramId, fd, ld);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}



	@GetMapping("/list-individual-id/{individualId}")              //Find list of IndividualProfRegistrations for a specific IndividualId
	public ResponseEntity<?> findListIndividualProfRegistrationForIndividualId(@PathVariable("individualId") Long individualId, ModelMap modelMap) {
		try {
			List<IndividualProfRegistration> result = individualProfRegistrationService.findListIndividualProfRegistrationForIndividualId(individualId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/list-professional-institute-id/{professionalInstituteId}")              //Find list of IndividualProfRegistrations for a specific ProfessionalInstituteId
	public ResponseEntity<?> findListIndividualProfRegistrationForProfessionalInstituteId(@PathVariable("professionalInstituteId") Long professionalInstituteId, ModelMap modelMap) {
		try {
			List<IndividualProfRegistration> result = individualProfRegistrationService.findListIndividualProfRegistrationForProfessionalInstituteId(professionalInstituteId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



}