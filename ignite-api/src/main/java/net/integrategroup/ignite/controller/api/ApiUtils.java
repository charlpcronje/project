package net.integrategroup.ignite.controller.api;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ApiUtils {

	public static final String PARAM_IDENTIFIER = "_";

	public static Map<String, Object> getParametersFromRequest(HttpServletRequest request) {
		HashMap<String, Object> data = new HashMap();
		
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				
				if (headerName.startsWith(PARAM_IDENTIFIER)) {
					String value = request.getHeader(headerName);
					String key = headerName.substring(PARAM_IDENTIFIER.length());
					
					data.put(key, value);
				}
			}
		}
		
		return data;
	}
	
	
}
