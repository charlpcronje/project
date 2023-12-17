package net.integrategroup.ignite.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.integrategroup.ignite.controller.api.AccessDeniedHandlerImpl;

/**
 *
 * @author Tony De Buys
 *
 */
@Order(1)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableMBeanExport(registration=RegistrationPolicy.IGNORE_EXISTING)
@PropertySource(value = "classpath:application.${ENV:local}.properties")
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	AccessDeniedHandlerImpl apiAccessDeniedHandler;

	@Bean
	public PasswordEncoder apiPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	AuthenticationEntryPoint forbiddenEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
	}

	// Source:
	// https://dev.to/betterjavacode/json-web-token-how-to-secure-spring-boot-rest-api-3cg2
	// https://dzone.com/articles/json-web-token-how-to-secure-spring-boot-rest-api
	// https://www.toptal.com/spring/spring-boot-oauth2-jwt-rest-protection
	// https://www.javainuse.com/spring/boot-jwt

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()

		.formLogin()
		.disable()

		.httpBasic()
		.disable()

		.logout()
		.disable()

		.csrf()
		.disable()

		.cors()
		.and()

		.exceptionHandling()
		.accessDeniedHandler(apiAccessDeniedHandler)
		;
	}

}
