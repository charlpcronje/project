package net.integrategroup.ignite.controller;

import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import net.integrategroup.ignite.utils.SecurityUtils;

@Controller
public class IgniteErrorController implements ErrorController {

	@Autowired
	SecurityUtils securityUtils;

	@GetMapping(value= {"/error", "/error/{code}"} )
	public Object handleError(@PathVariable Optional<Integer> code,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Integer statusCode = -1;

		if (status != null) {
			statusCode = Integer.valueOf(status.toString());
		}

		if ((code != null) && (code.isPresent())) {
			statusCode = code.get();
		}

		String message = "Error: " + statusCode + " - An unknown error has occurred.";
		if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
			// Could be missing or expired token
			message = "The request could not be authenticated.";
		}
		if(statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
			message = "The requested method is not allowed.";
		}
		if(statusCode == HttpStatus.NOT_FOUND.value()) {
			message = "The requested page could not be found.";
		}
		if(statusCode == HttpStatus.FORBIDDEN.value()) {
			message = "Access denied.";
		}
		else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			message = "An internal server error has occurred.";
		}

		model.addAttribute("message", message);
		return getErrorPath();
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
