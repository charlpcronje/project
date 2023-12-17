package net.integrategroup.ignite.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {

	/**
	 * Get a value from the HTTPServletRequest header, returning null
	 * if the requested key does not exist on the header
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getHeaderVariable(HttpServletRequest request, String key) {
		String result = null;
		Enumeration<String> headerNames = request.getHeaderNames();
		
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement(); 
				String value = request.getHeader(name);
				
				if (name.equalsIgnoreCase(key)) {
					result = value;
					break;
				}
			}
		}
		
		return result;
	}
	
}
