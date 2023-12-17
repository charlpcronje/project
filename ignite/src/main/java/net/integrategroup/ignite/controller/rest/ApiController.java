package net.integrategroup.ignite.controller.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.Api;
import net.integrategroup.ignite.persistence.service.ApiService;
import net.integrategroup.ignite.utils.IgniteUtils;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/api")
public class ApiController {

	@Autowired
	ApiService apiService;

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping()
	public ResponseEntity<?> getAllApis() {
		try {
			List<Api> result = apiService.findAll();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveApi(@RequestBody Api api) {
		String secret;

		try {
			// get the original API from the database...
			Api dbApi = apiService.findByApiKey(api.getApiKey());
			if (dbApi == null) {
				// it doesn't exist, create one...
				dbApi = new Api();
			}

			// set the data from the incoming data
			dbApi.setApiKey(api.getApiKey());
			dbApi.setApplicationName(api.getApplicationName());
			dbApi.setActiveFlag(api.getActiveFlag());

			// if we have a unhidden password...
			secret = api.getNaturalSecret();
			if (IgniteUtils.isValidPassword(secret)) {
				// encrypt it...
				String encryptedSecret = (new BCryptPasswordEncoder()).encode(secret);
				dbApi.setSecret(encryptedSecret);
			}

			// TODO: maybe we should send a mail to the admin with the unencrypted secret so that there is a record of it
			// because from this safe onwards we will not be able to retrieve the secret

			dbApi = apiService.save(dbApi);
			return ResponseEntity.ok(dbApi);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteApi(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			Long apiId = mu.getAsLongOrNull(data, "apiId");

			apiService.delete(apiId);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
