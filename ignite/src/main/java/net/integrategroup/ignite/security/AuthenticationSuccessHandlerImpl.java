package net.integrategroup.ignite.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import net.integrategroup.ignite.security.authentication.UserAuthentication;
import net.integrategroup.ignite.security.authentication.UserAuthenticationFactory;
import net.integrategroup.ignite.utils.SecurityUtils;

// Source:
// https://grokonez.com/spring-framework/spring-security/spring-security-customize-login-handler
// https://www.baeldung.com/spring_redirect_after_login
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	public Logger logger = Logger.getLogger(AuthenticationSuccessHandlerImpl.class.getName());

	@Autowired
	UserAuthenticationFactory userAuthenticationFactory;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// Add's the LDAP authentication to the userAuthentication
		UserAuthentication userAuthentication = userAuthenticationFactory.create(authentication);

		securityUtils.loginEvent(userAuthentication);
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);

		String username = userAuthentication.getName();

		logger.info("Login for: " + username + " detected");

		// set anything that we want to send back out to the UI
		// request.setAttribute("username", username);

		// Redirect to the homepage passing in the request and response
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		redirectStrategy.sendRedirect(request,  response, "/");
	}
}
