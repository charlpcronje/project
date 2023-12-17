package net.integrategroup.ignite.utils;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class RuntimeUtils {

	// @Autowired
	// BuildProperties buildProperties;

	public static Process execute(String command) throws IOException {
		Process p = Runtime.getRuntime().exec(command);

		return p;
	}

	/**
	 * Get the version string from the pom.xml file
	 *
	 * Source:
	 * https://www.vojtechruzicka.com/spring-boot-version/
	 *
	 * @return A string representing the version number of the application
	 *
	 */
	public String getVersion() {
		// TODO: using BuildProperties causes a missing bean error when creating cross project applications
		// TODO: Tony De Buys removed this 16/11/2023 - We need to find a different way of getting the version number
		// return buildProperties.getVersion();
		return "0.0.0";
	}
}
