package net.integrategroup.ignite.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.springframework.core.io.ClassPathResource;

/**
 * @author Tony De Buys
 *
 * Note: We have to get the properties by interrogating the environment variables
 *       and building up the name of the properties file. We have to do this because
 *       we cannot access components (via Autowired) in entities, etc
 *
 * Deprecated
 * Rather use @Value annotation in @Components and pass the value if needed
 */
@Deprecated
public class PropertiesUtils {
	private Logger logger = Logger.getLogger(PropertiesUtils.class.getName());

	private String getPropertiesFilename() throws IOException {
		String env = System.getenv("ENV");

		if ((env == null) || (env.isEmpty())) {
			env = "local";
		}

		String propertiesFilename = "application." + env + ".properties";
		File propertiesFile = new ClassPathResource(propertiesFilename).getFile();

		if (!propertiesFile.exists()) {
			logger.severe("Properties file: " + propertiesFilename + " not found");
			throw new IOException("Properties file not found.");
		}

		return propertiesFile.getAbsolutePath();
	}

	private String getProperty(String key) throws IOException {
		Properties properties = new Properties();
		String result = null;
		FileInputStream is = null;

		try {
			File f = new File(getPropertiesFilename());
			is = new FileInputStream(f);
			properties.load(is);

			result = properties.getProperty(key);
		} finally {
			IgniteUtils.closeQuietly(is);
		}

		return result;
	}

	public String getReportsPath() throws Exception {
		String result = getProperty(IgniteConstants.PROPERTIES_REPORTS_PATH);
		result = IgniteUtils.getTerminatedPath(result);

		return result;
	}

}
