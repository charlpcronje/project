package net.integrategroup.ignite.controller.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.email.EmailUtils;
import net.integrategroup.ignite.persistence.model.KeyValuePair;
import net.integrategroup.ignite.persistence.service.KeyValuePairService;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.IgniteUtils;
import net.integrategroup.ignite.utils.MapUtils;

/**
 * @author Tony De Buys
 *
 */
@RestController
@RequestMapping("/rest/ignite/v1/parameter")
public class KeyValuePairController {

	@Autowired
	KeyValuePairService keyValuePairService;

	@GetMapping("")
	public ResponseEntity<?> getKeyValuePairs() {
		try {
			List<KeyValuePair> result = keyValuePairService.findGeneralParameters();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("")
	public ResponseEntity<?> saveParameter(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();
			String keyName = mu.getAsStringOrNull(data, "keyName");
			String value = mu.getAsStringOrNull(data, "value");

			KeyValuePair keyValuePair = saveKeyValuePair(keyName, value);

			return ResponseEntity.ok(keyValuePair);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private KeyValuePair saveKeyValuePair(String keyName, String value) throws Exception {
		KeyValuePair keyValuePair = keyValuePairService.findByKeyName(keyName);

		if (keyValuePair == null) {
			keyValuePair = new KeyValuePair();
			keyValuePair.setKeyName(keyName);
		}

		keyValuePair.setValue(value);
		keyValuePair = keyValuePairService.save(keyValuePair);

		return keyValuePair;
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteParameter(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();
			String keyName = mu.getAsStringOrNull(data, "keyName");

			KeyValuePair keyValuePair = keyValuePairService.findByKeyName(keyName);

			if (keyValuePair == null) {
				throw new Exception("Key Value Parameter not found");
			}

			keyValuePairService.delete(keyValuePair);

			return ResponseEntity.ok(keyValuePair);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("mail")
	public ResponseEntity<?> saveMailParameters(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			String mailEnabled = mu.getAsStringOrNull(data, "mailEnabled");
			String mailServerName = mu.getAsStringOrNull(data, "mailServerName");
			String mailServerPort = mu.getAsStringOrNull(data, "mailServerPort");
			String mailSmtpUsername = mu.getAsStringOrNull(data, "mailSmtpUsername");
			String mailSmtpPassword = mu.getAsStringOrNull(data, "mailSmtpPassword");
			String mailProxyEnabled = mu.getAsStringOrNull(data, "mailProxyEnabled");
			String mailProxyServerName = mu.getAsStringOrNull(data, "mailProxyServerName");
			String mailProxyServerPort = mu.getAsStringOrNull(data, "mailProxyServerPort");

			saveKeyValuePair(IgniteConstants.KEY_MAIL_ENABLED, mailEnabled);
			saveKeyValuePair(IgniteConstants.KEY_MAIL_SERVER_NAME, mailServerName);
			saveKeyValuePair(IgniteConstants.KEY_MAIL_SERVER_PORT, mailServerPort);
			saveKeyValuePair(IgniteConstants.KEY_MAIL_SMTP_USERNAME, mailSmtpUsername);
			saveKeyValuePair(IgniteConstants.KEY_MAIL_PROXY_ENABLED, mailProxyEnabled);
			saveKeyValuePair(IgniteConstants.KEY_MAIL_PROXY_SERVER_NAME, mailProxyServerName);
			saveKeyValuePair(IgniteConstants.KEY_MAIL_PROXY_SERVER_PORT, mailProxyServerPort);

			// In the UI we show the password as *** so that the user knows there is a password.
			// We only save the password if the user has typed a new password. (It will therefore be something other than ***)
			if (IgniteUtils.isValidPassword(mailSmtpPassword)) {
				saveKeyValuePair(IgniteConstants.KEY_MAIL_SMTP_PASSWORD, mailSmtpPassword);
			}

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/mail/test")
	public ResponseEntity<?> sendTestMail(@RequestBody HashMap<String, Object> data) {
		try {
			EmailUtils emailUtils = new EmailUtils();

			MapUtils mu = new MapUtils();
			String serverName = mu.getAsStringOrNull(data, "mailServerName");
			String serverPort = mu.getAsStringOrNull(data, "mailServerPort");
			String smtpUsername = mu.getAsStringOrNull(data, "mailSmtpUsername");
			String smtpPassword = mu.getAsStringOrNull(data, "mailSmtpPassword");
			String proxyEnabled = mu.getAsStringOrNull(data, "mailProxyEnabled");
			String proxyServerName = mu.getAsStringOrNull(data, "mailProxyServerName");
			String proxyPort = mu.getAsStringOrNull(data, "mailProxyServerPort");
			String sender = smtpUsername; // todo: configure sender name

			// if smtpPassword is all *** then get it from the db
			if ((smtpPassword != null) && (!smtpPassword.isEmpty()) && (smtpPassword.equals(IgniteUtils.obfuscatePassword(smtpPassword)))) {
				smtpPassword = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SMTP_PASSWORD);
			}

			emailUtils.sendTestMail(serverName, serverPort, sender, IgniteConstants.FLAG_YES.equals(proxyEnabled), proxyServerName, proxyPort, smtpUsername, smtpPassword);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
