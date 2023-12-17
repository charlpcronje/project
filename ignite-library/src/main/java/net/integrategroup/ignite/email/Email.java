package net.integrategroup.ignite.email;

import java.util.ArrayList;
import java.util.List;

public class Email {

	private boolean useProxy = false;
	private String proxy = null;
	private String proxyPort = null;

	private String recipient = null;
	private String sender = null;
	private String subject = null;
	private String body = null;

	private List<String> attachments = new ArrayList<>();

	private String mailServer = null;
	private String mailPort = null;

	private String smtpUsername = null;
	private String smtpPassword = null;

	public String getProxy() {
		return proxy;
	}
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	public String getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public String getMailPort() {
		return mailPort;
	}

	public void setMailPort(String mailPort) {
		this.mailPort = mailPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}
	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}
	public String getSmtpPassword() {
		return smtpPassword;
	}
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public void send() throws Exception {
		Email email = null;

		if (mailServer == null) {
			throw new Exception("Mail has not been configured.");
		}

		email = new GoogleMail();

		// Pass on all the parameters
		email.useProxy = useProxy;
		email.proxy = proxy;
		email.proxyPort = proxyPort;
		email.recipient = recipient;
		email.sender = sender;
		email.subject = subject;
		email.body = body;
		email.mailServer = mailServer;
		email.mailPort = mailPort;
		email.smtpUsername = smtpUsername;
		email.smtpPassword = smtpPassword;
		email.attachments = attachments;

		email.send();
	}

	public void attach(String attachFilename) {
		attachments.add(attachFilename);
	}

	public List<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	public boolean isUseProxy() {
		return useProxy;
	}
	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}

}
