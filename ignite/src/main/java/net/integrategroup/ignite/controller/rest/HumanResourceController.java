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

import net.integrategroup.ignite.persistence.model.HumanResource;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.HumanResourceService;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/human-resource")
public class HumanResourceController {

	@Autowired
	HumanResourceService humanResourceService;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping("/{participantId}")
	public ResponseEntity<?> getHumanResources(ModelMap modelMap, @PathVariable(name = "participantId") Long participantId) {
		try {
			List<HumanResource> result = humanResourceService.findHumanResources(participantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveHumanResource(@RequestBody HumanResource humanResource) {
		try {
			HumanResource test = humanResourceService.findByHumanResourceId(humanResource.getHumanResourceId());
			if (test == null) {
				throw new Exception("Human Resource not found");
			}
			humanResource = humanResourceService.save(humanResource);

			return ResponseEntity.ok(humanResource);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("new")
	public ResponseEntity<?> saveHumanResourceNew(@RequestBody HumanResource humanResource) {
		try {
			HumanResource test = humanResourceService.findByHumanResourceId(humanResource.getHumanResourceId());
			if (test != null) {
				throw new Exception("The Human Resource already exists");
			}
			humanResource = humanResourceService.save(humanResource);

			return ResponseEntity.ok(humanResource);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteHumanResource(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long humanResourceId = mu.getAsLongOrNull(data, "humanResourceId");

		String sql = "CALL ig_db.deleteHumanResource(?);";

		System.out.println ("CALL ig_db.deleteHumanResource(" + humanResourceId +");");
		try {
			Object[] params = {
					humanResourceId
			};
			
			databaseService.executeStoredProc(sql, params);
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}