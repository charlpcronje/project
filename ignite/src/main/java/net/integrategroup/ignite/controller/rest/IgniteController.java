package net.integrategroup.ignite.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.utils.RuntimeUtils;

/**
 * @author Tony De Buys
 *
 */
@RestController
@RequestMapping("/rest/ignite/v1")
public class IgniteController {

	@Autowired
	RuntimeUtils runtimeUtils;

	@GetMapping("/version")
	public ResponseEntity<?> getVersion() {
		Map<String, Object> result = new HashMap<>();

		result.put("version", runtimeUtils.getVersion());

		return ResponseEntity.ok(result);
	}

}
