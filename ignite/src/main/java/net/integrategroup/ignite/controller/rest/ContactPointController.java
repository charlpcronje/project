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

import net.integrategroup.ignite.persistence.model.ContactPoint;
import net.integrategroup.ignite.persistence.model.VContactPoint;
import net.integrategroup.ignite.persistence.service.ContactPointService;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/contact-point")
public class ContactPointController {

	@Autowired
	ContactPointService contactPointService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;


	@GetMapping()
	public ResponseEntity<?> getContactPoints() {
		try {
			List<ContactPoint> contactPoints = contactPointService.findAll();
			return ResponseEntity.ok(contactPoints);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/*
	@GetMapping("/type")
	public ResponseEntity<?> getContactPointTypes() {
		try {
			List<ContactPointType> contactPointTypes = contactPointService.getContactPointTypes();
			return ResponseEntity.ok(contactPointTypes);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	*/

	@GetMapping("/office/{participantOfficeId}")
	public ResponseEntity<?> getContactPointsForOffice(@PathVariable("participantOfficeId") Long participantOfficeId) {
		try {
			List<ContactPoint> contactPoints = contactPointService.getContactPointsForOffice(participantOfficeId);
			return ResponseEntity.ok(contactPoints);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping()
	public ResponseEntity<?> saveContactPoint(@RequestBody ContactPoint contactPoint) {
		try {

			ContactPoint test = contactPointService.findByContactPointId(contactPoint.getContactPointId());
			if (test == null) {
				throw new Exception("Contact Point not found");
			}

			contactPoint = contactPointService.save(contactPoint);
			return ResponseEntity.ok(contactPoint);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/new")
	public ResponseEntity<?> saveContactPointNew(@RequestBody ContactPoint contactPoint) {
		try {

			ContactPoint test = contactPointService.findByContactPointId(contactPoint.getContactPointId());
			if (test != null) {
				throw new Exception("Contact Point already exists");
			}
			contactPoint = contactPointService.save(contactPoint);
			return ResponseEntity.ok(contactPoint);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}



	@PostMapping("/delete")
	public ResponseEntity<?> deleteContactPoint(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long contactPointId = mu.getAsLongOrNull(data, "contactPointId");

		String sql = "CALL ig_db.deleteContactPoint(?);";

		System.out.println ("CALL ig_db.deleteContactPoint(" + contactPointId+");");
		try {
			Object[] params = {
					contactPointId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/list-view-contact-point-for-participant-office/{participantOfficeId}")                           //Find List from View that needs parameter
	public ResponseEntity<?> findListVContactPointsForOffice(@PathVariable("participantOfficeId") Long participantOfficeId,	ModelMap modelMap)  {
		try {
			List<VContactPoint> result = contactPointService.findListVContactPointsForOffice(participantOfficeId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}

