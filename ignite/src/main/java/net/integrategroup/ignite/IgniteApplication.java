package net.integrategroup.ignite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Tony De Buys
 *
 */
@SpringBootApplication
@EnableScheduling
public class IgniteApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(IgniteApplication.class, args);
	}

}
