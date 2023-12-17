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

import net.integrategroup.ignite.persistence.model.InstituteQualification;
import net.integrategroup.ignite.persistence.model.VInstituteQualification;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.InstituteQualificationService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;



/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-16 19:51:56 ******** ***** **/


@RestController
@RequestMapping("/rest/ignite/v1/institute-qualification")

public class InstituteQualificationController {

	@Autowired
	InstituteQualificationService instituteQualificationService;

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@GetMapping("/find-all")          //All records in the InstituteQualification Table
	public ResponseEntity<?> findAll() {
		try {
			List<InstituteQualification> result = instituteQualificationService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/by-institute-qualification-id/{instituteQualificationId}")   //Find the record by the PrimaryKey
	public ResponseEntity<?> getByInstituteQualificationId(@PathVariable("instituteQualificationId") Long instituteQualificationId) {
		try {
			InstituteQualification result = instituteQualificationService.getByInstituteQualificationId(instituteQualificationId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()       //Save (update)
	public ResponseEntity<?> saveInstituteQualification(@RequestBody InstituteQualification instituteQualification) {
		try {
			InstituteQualification test = instituteQualificationService.getByInstituteQualificationId(instituteQualification.getInstituteQualificationId());
			if (test == null) {
				throw new Exception("InstituteQualification not found");
			}
			instituteQualification = instituteQualificationService.save(instituteQualification);
			return ResponseEntity.ok(instituteQualification);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/new")   //Save (new)
	public ResponseEntity<?> saveInstituteQualificationNew(@RequestBody InstituteQualification instituteQualification) {
		try {
			InstituteQualification test = instituteQualificationService.getByInstituteQualificationId(instituteQualification.getInstituteQualificationId());
			if (test != null) {
				throw new Exception("The InstituteQualification already exists");
			}
			instituteQualification = instituteQualificationService.save(instituteQualification);
			return ResponseEntity.ok(instituteQualification);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@GetMapping("/by-view-institute-qualification-id/{instituteQualificationId}")   //One record from View
//	public ResponseEntity<?> getByVInstituteQualificationId(@PathVariable("instituteQualificationId") Long instituteQualificationId) {
//		try {
//			VInstituteQualification result = instituteQualificationService.getByVInstituteQualificationId(instituteQualificationId);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}


	@PostMapping("/delete")   //Delete a record, and insert into AuditTrial table
	public ResponseEntity<?> deleteInstituteQualification(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();

		Long instituteQualificationId = mu.getAsLongOrNull(data, "instituteQualificationId");
		String sql = "CALL ig_db.deleteInstituteQualification(?, ?);";

		String exampleSql = "CALL ig_db.deleteInstituteQualification(" + instituteQualificationId + ", '"
			+ securityUtils.getUsername() + "');";
		System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

		try {	//**//					
			Object[] params = {		
					instituteQualificationId,
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

	@GetMapping("/find-all-in-view")                                            //All in view
	public ResponseEntity<?> findListAllInView()  {
		try {
			List<VInstituteQualification> result = instituteQualificationService.findListAllInView();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@GetMapping("/list-view-institute-qualification-for-XXX")                                       //Find List from View without parameter
//	public ResponseEntity<?> findListInstituteQualificationForXXX()  {
//		try {
//			List<VInstituteQualification> result = instituteQualificationService.findListVInstituteQualificationForXXX();
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@GetMapping("/list-view-institute-qualification-for-study-institute/{studyInstituteId}")                           //Find List from View that needs parameter
	public ResponseEntity<?> findListVInstituteQualificationForStudyInstitute(@PathVariable("studyInstituteId") Long studyInstituteId,	ModelMap modelMap)  {
		try {
			List<VInstituteQualification> result = instituteQualificationService.findListVInstituteQualificationForStudyInstitute(studyInstituteId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@GetMapping("/list-view-institute-qualification-for-XXX/{paramId}/{paramName}/{paramName2}")   //Find List from View that needs multiple parameters
//	public ResponseEntity<?> findListVInstituteQualificationForXXX(@PathVariable("paramId") Long paramId,
//		                                        @PathVariable("paramName") Long paramName,
//		                                        @PathVariable("paramName") Long paramName) {
//		try {
//			List<VInstituteQualification> result = instituteQualificationService.findListVInstituteQualificationForXXX(paramId, paramName, paramName2);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

//	@GetMapping("/list-view-institute-qualification-for-XXX/{paramId}/{firstDay}/{lastDay}")   //Find List from View that needs date parameters   (use variable like this:  var dateParam = getMsFromDatePicker("theStartDate");)
//	public ResponseEntity<?> findListInstituteQualificationForXXX(ModelMap modelMap,
//		                                        @PathVariable("paramId") Long paramId,
//		                                        @PathVariable(name = "firstDay") Long firstDay,
//		                                        @PathVariable(name = "lastDay") Long lastDay) {
//		try {
//			Date fd = new Date(firstDay);     // Dates sent to db
//			Date ld = new Date(lastDay);
//			List<VInstituteQualification> result = instituteQualificationService.findListVInstituteQualificationForXXX(paramId, fd, ld);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}



	@GetMapping("/list-study-institute-id/{studyInstituteId}")              //Find list of InstituteQualifications for a specific StudyInstituteId
	public ResponseEntity<?> findListInstituteQualificationForStudyInstituteId(@PathVariable("studyInstituteId") Long studyInstituteId, ModelMap modelMap) {
		try {
			List<InstituteQualification> result = instituteQualificationService.findListInstituteQualificationForStudyInstituteId(studyInstituteId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



}