package net.integrategroup.ignite.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class GoogleMail extends Email {

	// Based on source:
	// https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/

	/*
	 *
	 * From Linode:
	 *
	 * If you have a need to send mail from your Linode, we ask that you first configure (1) valid DNS A records and (2) rDNS records for any
	 * Linodes that you plan to use to send mail. Then, open a Support ticket from the Linode Manager – we’ll ask you to provide the name
	 * of the Linode(s) that will be used for mailing.
	 *
	 * Links:
	 * 1) https://www.linode.com/docs/guides/dns-manager/#add-dns-records
	 * 2) https://www.linode.com/docs/networking/dns/configure-your-linode-for-reverse-dns/
	 *
	 */

	@Override
	public void send() throws EmailException, AddressException, MessagingException {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", getMailServer());
		prop.put("mail.smtp.port", getMailPort());
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", getMailPort());
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop,
				new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getSmtpUsername(), getSmtpPassword());
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(getSender()));
		message.setRecipients(
				Message.RecipientType.TO,
				InternetAddress.parse(getRecipient())
				);
		message.setSubject(getSubject());
		message.setContent(getBody(), "text/html; charset=utf-8");

		Transport.send(message);
	}

	public void old_send() throws EmailException {
		MultiPartEmail email = new MultiPartEmail();

		int port = Integer.parseInt(getMailPort());
		email.setHostName(getMailServer());
		email.setSmtpPort(port);

		email.setFrom(getSender(), getSender());

		String recipients = getRecipient();
		if (recipients.contains(";")) {
			String[] recipientsList = recipients.split(";");
			for (String recipient : recipientsList) {
				email.addTo(recipient);
			}
		} else {
			email.addTo(recipients);
		}

		email.setSubject(getSubject());
		email.setMsg(getBody());

		// Source: https://commons.apache.org/proper/commons-email/userguide.html
		for (String attachmentFilename: getAttachments()) {
			// Create the attachment
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(attachmentFilename);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);

			// TODO; do we need a description?
			//attachment.setDescription("Picture of John");

			// TODO: extract the filename only
			attachment.setName(attachmentFilename);

			email.attach(attachment);
		}

		email.send();
	}


}


