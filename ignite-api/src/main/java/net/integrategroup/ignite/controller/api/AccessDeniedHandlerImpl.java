package net.integrategroup.ignite.controller.api;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

	private Logger logger = Logger.getLogger(AccessDeniedHandlerImpl.class.getName());

	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException exception) throws IOException, ServletException {
		logger.severe(exception.getMessage());

		response.sendError(HttpStatus.FORBIDDEN.value());
	}

}
