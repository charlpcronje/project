package net.integrategroup.ignite.utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tony De Buys
 *
 * Utility class to generate a token and an expiry date for the token.
 * Note that your code must store this token and expiry date itself.
 *
 * Usage adapted from https://www.baeldung.com/spring-security-registration-i-forgot-my-password
 *
 */
public class PasswordResetToken implements Serializable {

	private static final long serialVersionUID = 3338034173232492702L;

	private static final int EXPIRATION = 60 * 24; // 24 hours

	private String token = null;

	private Date expiryDate = null;

	public PasswordResetToken() {
		token = UUID.randomUUID().toString();
		token = token.replace("-", "");
		System.out.println("passwordResetToken");

		Calendar date = Calendar.getInstance();
		date.add(Calendar.MINUTE, EXPIRATION);

		expiryDate = date.getTime();
	}

	public String getToken() {
		return token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}
}
