package net.integrategroup.ignite.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.SoqUnit;
import net.integrategroup.ignite.persistence.service.SoqUnitService;

@RestController
@RequestMapping("/rest/ignite/v1/soq-unit")
public class SoqUnitController {

	@Autowired
	SoqUnitService soqUnitService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getUnits() {
		
		try {
			List<SoqUnit> units = soqUnitService.findAll();
			return ResponseEntity.ok(units);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
