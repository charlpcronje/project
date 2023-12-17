package net.integrategroup.ignite.email;

import net.integrategroup.ignite.persistence.service.KeyValuePairService;
import net.integrategroup.ignite.utils.IgniteConstants;

/**
 * @author Tony De Buys
 *
 */
public class EmailUtils {

	public void sendTestMail(String serverName, String serverPort,
			String sender,
			boolean proxyEnabled, String proxyServerName, String proxyServerPort,
			String smtpUsername, String smtpPassword) throws Exception {

		/*
		email-enabled=true
		email-sender=dbx.notification@gmail.com
		email-server-name=smtp.gmail.com
		email-server-port=465
		email-smtp-password=!dbxN0t1f1c@t10n5?
		email-smtp-username=dbx.notification@gmail.com

		proxy-enabled=false
		proxy-server-name=proxysouth.mud.internal.co.za
		proxy-server-port=8080

		 */

		Email email = new Email();

		email.setSender(sender);

		email.setMailServer(serverName);
		email.setMailPort(serverPort);

		if ((smtpUsername != null) && (!smtpUsername.isEmpty())) {
			email.setSmtpUsername(smtpUsername);
			email.setSmtpPassword(smtpPassword);
		}

		if (proxyEnabled) {
			email.setProxy(proxyServerName);
			email.setProxyPort(proxyServerPort);
		}
		email.setUseProxy(proxyEnabled);

		String emailMsg = "This is a test message from Ignite.\n\nPlease ignore this mail.\n\n";

		email.setSubject("Ignite: Test email");
		email.setBody(emailMsg);
		email.setRecipient("tony.debuys@gmail.com");
		email.send();
	}

	public void send(KeyValuePairService keyValuePairService, String recipient, String subject, String body ) throws Exception {
		// Get our e-mail settings
		String mailEnabled = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_ENABLED);

		String mailServerName = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SERVER_NAME);
		String mailServerPort = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SERVER_PORT);

		String mailSmtpUsername = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SMTP_USERNAME);
		String mailSmtpPassword = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_SMTP_PASSWORD);

		String mailProxyEnabled = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_PROXY_ENABLED);
		String mailProxyServer = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_PROXY_SERVER_NAME);
		String mailProxyPort = keyValuePairService.getKeyValue(IgniteConstants.KEY_MAIL_PROXY_SERVER_PORT);

		if(IgniteConstants.FLAG_NO.equalsIgnoreCase(mailEnabled)) {
			return;
		}

		Email email = new Email();
		email.setMailServer(mailServerName);
		email.setMailPort(mailServerPort);
		email.setSender(mailSmtpUsername);
		email.setSmtpUsername(mailSmtpUsername);
		email.setSmtpPassword(mailSmtpPassword);

		if(IgniteConstants.FLAG_YES.equalsIgnoreCase(mailProxyEnabled)) {
			email.setUseProxy(true);
			email.setProxy(mailProxyServer);
			email.setProxyPort(mailProxyPort);
		}

		email.setRecipient(recipient);
		email.setSubject(subject);
		email.setBody(body);
		email.send();

	}


}
