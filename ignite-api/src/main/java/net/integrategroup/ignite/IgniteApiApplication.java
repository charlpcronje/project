package net.integrategroup.ignite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Tony De Buys
 *
 */
@SpringBootApplication
public class IgniteApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(IgniteApiApplication.class, args);
	}

}
