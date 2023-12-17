package net.integrategroup.ignite.controller.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.util.Log;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.Bank;
import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.repository.BankRepository;
import net.integrategroup.ignite.persistence.service.ApiService;
import net.integrategroup.ignite.persistence.service.IndividualService;
import net.integrategroup.ignite.utils.HttpUtils;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ApiController {
	private Logger logger = Logger.getLogger(ApiController.class.getName());

	@Autowired
	SecurityUtils securityUtils;

	@Autowired 
	ApiService apiService;
	
	@Autowired
	IndividualService individualService;

	@ExceptionHandler({ Exception.class })
	public void handleException(HttpServletResponse response, Exception e) throws IOException {
		e.printStackTrace();

		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@PostMapping
	public ResponseEntity<?> login(HttpServletRequest request, @RequestBody HashMap<String, Object> data) {
		JSONObject result = new JSONObject();

		MapUtils mu = new MapUtils();

		// Get the username and password
		String username = mu.getAsStringOrNull(data, "username");
		String password = mu.getAsStringOrNull(data, "password");
		
		try {
			String apiKey = HttpUtils.getHeaderVariable(request, "Api-Key");

			if (!apiService.isValidApiKey(apiKey)) {
				logger.severe("Access denied: " + apiKey + " is not a valid API Key");
				throw new Exception("Invalid API Key");
			}
			
			if ((username == null) || (password == null)) {
				logger.severe("Access denied: Null username or password recieved");
				throw new Exception("Empty credentials specified");
			}
			
			// We have a valid API key, now test the username and password
			Individual individual = individualService.findByUsername(username);

			boolean throwInvalidUsernamePassword = false;
			if (individual == null) {
				// No such individual (username)
				logger.severe("Access denied: No such user: " + username); 
				throwInvalidUsernamePassword  = true;
			} else {
				if (!IgniteConstants.FLAG_YES.equalsIgnoreCase(individual.getAllowLoginFlag())) {
					// The user is not allowed to log in
					logger.severe("Access denied: " + username + " is not permitted to log in (Disabled in Ignite)");
					throwInvalidUsernamePassword  = true;
				}
				
				// Test the password using the passwordEncoder
				boolean passwordMatches = passwordEncoder().matches(password, individual.getPass());
				if (!passwordMatches) {
					// passwords dont match 
					logger.severe("Access denied: Invalid password for "+ username);
					throwInvalidUsernamePassword  = true;
				}
			}
				
			if (throwInvalidUsernamePassword) {
				result.put("result", "failed");
				result.put("message", "Invalid username or password");
			} else {
				result.put("result", "success");
				result.put("message", "Logged in successfully");
				result.put("username", username);
				result.put("fullname", individual.getDisplayName());

				logger.info(username + " successfully logged in");
			}
		} catch (Exception e) {	
			result.put("result", "failed");
			result.put("message", e.getMessage());

			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result.toString(2));
		}

		return ResponseEntity.ok(result.toString(2));
	}
	
}
