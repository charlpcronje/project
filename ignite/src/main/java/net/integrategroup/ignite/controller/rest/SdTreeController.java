package net.integrategroup.ignite.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.VServiceDiscipline;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.SdTreeService;
import net.integrategroup.ignite.utils.SecurityUtils;


@RestController
@RequestMapping("/rest/ignite/v1/service-discipline-tree")

public class SdTreeController {

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	SdTreeService sdTreeService;

	@Autowired
	DatabaseService databaseService;

	@GetMapping()
	public ResponseEntity<?> getSdTree(ModelMap modelMap) {
		try {
			List<VServiceDiscipline> result = sdTreeService.getTree();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/children/{id}")
	public ResponseEntity<?> getChildren(@PathVariable("id") Long serviceDisciplineIdParent) {
		try {
			List<VServiceDiscipline> result = sdTreeService.getChildren(serviceDisciplineIdParent);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
