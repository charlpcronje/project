package net.integrategroup.ignite.security;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.integrategroup.ignite.utils.IgniteConstants;

/**
 * @author Tony De Buys
 *
 */
@Order(2)
@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableMBeanExport(registration=RegistrationPolicy.IGNORE_EXISTING)
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@PropertySource(value = "classpath:application.${ENV:local}.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	final private String SQL_USER_BY_USERNAME = "SELECT username, pass, 1 FROM ig_db.Individual WHERE username=? AND allowLoginFlag = 'Y'";
	final private String SQL_AUTHORITIES_BY_USERNAME = "SELECT username, 'ROLE_USER' FROM ig_db.Individual WHERE username=?";

	private Logger logger = Logger.getLogger(WebSecurityConfig.class.getName());

	@Autowired
	DataSource dataSource;

	@Autowired
	AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;

	@Value("${spring.datasource.url}")
	String databaseUrl;

	@Bean
	public PasswordEncoder webPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		logger.info("Database connection: " + databaseUrl);

		http

		// Fix to allow iframes in pages, from the same URL
		.headers().frameOptions().sameOrigin().and()

		.authorizeRequests()
		.antMatchers("/forgot-password**").permitAll()
		.antMatchers("/profile-reset/**").permitAll()
		.antMatchers("/**/profile/reset/**").permitAll()
		.antMatchers("/**/profile/set/**").permitAll()

		.anyRequest().fullyAuthenticated()

		/*
		 * everything requires that you be authenticated...
		 * individual controller requests, can check for associated roles and permissions
		 *
		 */

		.and()
		.formLogin()
		.loginPage("/login").permitAll()
		.failureUrl("/login?error")
		.successHandler(authenticationSuccessHandlerImpl)

		// Remember Me source: https://www.baeldung.com/spring-security-remember-me
		.and()
		.rememberMe().rememberMeParameter("rememberMe").key(IgniteConstants.IGNITE_UNIQUE_KEY)

		.and()
		.logout().permitAll();
	}

	// TODO: for adding users with secured passwords: https://grobmeier.solutions/spring-security-5-using-jdbc.html
	// TODO: might need to upgrade the security to JWT: https://grobmeier.solutions/spring-security-5-jwt-basic-auth.html

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.jdbcAuthentication()

		.usersByUsernameQuery(SQL_USER_BY_USERNAME)
		.authoritiesByUsernameQuery(SQL_AUTHORITIES_BY_USERNAME)

		.dataSource(dataSource)
		.passwordEncoder(webPasswordEncoder())
		;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		.ignoring()
		.antMatchers(
				"/assets/**",
				"/css/**",
				"/font-awesome/**",
				"/fonts/**",
				"/ignite/**",
				"img/**",
				"/js/**");
	}

}
