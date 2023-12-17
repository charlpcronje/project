package net.integrategroup.ignite.controller.rest;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.email.EmailUtils;
import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.KeyValuePair;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.IndividualService;
import net.integrategroup.ignite.persistence.service.KeyValuePairService;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.PasswordResetToken;
import net.integrategroup.ignite.utils.SecurityUtils;

/**
 * @author Tony De Buys
 *
 */
@RestController
@RequestMapping("/rest/ignite/v1/profile")
public class ProfileController {
//	private Logger logger = Logger.getLogger(ProfileController.class.getName());

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	KeyValuePairService keyValuePairService;

	@Autowired
	IndividualService individualService;

	@PostMapping("")
	public ResponseEntity<?> saveProfile(@RequestBody HashMap<String, Object> data) {
		MapUtils mu = new MapUtils();

		String username = mu.getAsStringOrNull(data, "username");
		String firstName = mu.getAsStringOrNull(data, "firstName");
		String initials = mu.getAsStringOrNull(data, "initials");
		String lastName = mu.getAsStringOrNull(data, "lastName");
		String idNumber = mu.getAsStringOrNull(data, "idNumber");

		try {
			if ((username == null) || (username.isEmpty())) {
				throw new Exception("Cannot update a Profile with an empty username");
			}

			if (!username.equals(securityUtils.getUsername())) {
				throw new Exception("Cannot update a Profile other than the logged in user");
			}

			Individual individual = individualService.findByUsername(username);
			individual.setFirstName(firstName);
			individual.setInitials(initials);
			individual.setLastName(lastName);
			individual.setIdNumber(idNumber);

			individualService.save(individual);

			return ResponseEntity.ok().build();

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/reset")
	public ResponseEntity<?> resetProfile(@RequestBody HashMap<String, Object> data) {
		MapUtils mu = new MapUtils();

		try {
			String email = mu.getAsStringOrNull(data, "email");
			KeyValuePair keyValuePair = keyValuePairService.findByKeyName(IgniteConstants.KEY_SITE_BASE_URL);
			String urlBase = keyValuePair.getValue();
			String url = null;

			// TODO: is this the right method - will the username always be an email address?
			Individual individual = individualService.findByUsername(email);

			if (individual == null) {
				throw new Exception("The email address entered in is not in our records.");
			}

			// Generate a token and save it so that we can validate the users attempt at resetting the password
			PasswordResetToken token = new PasswordResetToken();
			individual.setPasswordResetToken(token);
			individualService.save(individual);

			if ((urlBase != null) && (!urlBase.endsWith("/"))) {
				urlBase += "/";
			}
			//url = urlBase + "profile-reset?token=" + token.getToken();

	        url = "http://127.0.0.1:8081/profile-reset?token=" + token.getToken();

			String emailMessage = "You recently requested to reset your password." +
					"<br><br>" +
					"Please follow this link to reset it: " +
					"<a href='" + url + "'>" + url + "</a>" +
					"<br><br>" +
					"If you did not request a password reset, please ignore this email. " +  // or reply to let us know
					"This password reset is only valid for the next 24 hours";

			EmailUtils eu = new EmailUtils();
			eu.send(keyValuePairService, email, "Ignite Password Reset", emailMessage);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/set")
	public ResponseEntity<?> setProfilePassword(@RequestBody HashMap<String, Object> data) {
		MapUtils mu = new MapUtils();

		try {
			Long userId = mu.getAsLongOrNull(data, "userId");
			String token = mu.getAsStringOrNull(data, "token");
			String password = mu.getAsStringOrNull(data, "password");
			password = (new BCryptPasswordEncoder()).encode(password);

			Individual individual = individualService.findByIndividualId(userId);

			if (individual == null) {
				throw new Exception("Cannot find the user");
			}

			if ((individual != null) && (!token.equals(individual.getPasswordResetToken()))) {
				throw new Exception("The users token does not match the given token");
			}

			if (password.isEmpty()) {
				throw new Exception("Cannot process and empty keyword");
			}

			// Voodoo code because we don't want the password on the individual in the model
			String sql = "UPDATE ig_db.Individual " +
					"SET Pass='" + password + "', " +
					"PasswordResetToken = null, " +
					"PasswordResetExpiryDate = null " +
					"WHERE PasswordResetToken='" + token + "' " +
					"AND IndividualId=" + userId;
			databaseService.execute(sql);


			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/change")
	public ResponseEntity<?> changeProfilePassword(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			String currentPwd = mu.getAsStringOrNull(data, "currentPwd");
			String newPwd = mu.getAsStringOrNull(data, "newPwd");

			String username = securityUtils.getUsername();

			Individual individual = individualService.findByUsername(username);

			if (individual == null) {
				throw new Exception("The Individual could not be found");
			}

			// test the current password
			if (!passwordEncoder.matches(currentPwd, individual.getPass())) {
				throw new Exception("The submitted password does not match the users current password");
			}

			// update the password with the new encrypted pwd
			individual.setPass(passwordEncoder.encode(newPwd));
			individual = individualService.save(individual);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
