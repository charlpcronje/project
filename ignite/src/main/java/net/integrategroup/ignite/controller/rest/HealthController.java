package net.integrategroup.ignite.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.Health;
import net.integrategroup.ignite.persistence.service.HealthService;
import net.integrategroup.ignite.schedule.HealthMonitorTask;

/**
 * @author Tony De Buys
 *
 */
@RestController
@RequestMapping("/rest/ignite/v1/health")
public class HealthController {

	@Autowired
	HealthService healthService;

	@Autowired
	HealthMonitorTask healthMonitor;

	@GetMapping
	public ResponseEntity<?> getHealthStatus() {
		try {
			List<Health> result = healthService.findAll();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> executeHealthMonitor() {
		try {
			healthMonitor.execute();

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
