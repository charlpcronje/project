package net.integrategroup.ignite.utils;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import net.integrategroup.ignite.persistence.model.Individual;
import net.integrategroup.ignite.persistence.model.Permission;
import net.integrategroup.ignite.persistence.repository.IndividualRepository;
import net.integrategroup.ignite.persistence.repository.PermissionRepository;
import net.integrategroup.ignite.security.authentication.UserAuthentication;

/**
 * Security based utility methods
 *
 * @author Tony De Buys
 *
 */
@Component
public class SecurityUtils {

	/* ************************************************************************
	 * ** NOTICE **************************************************************
	 * ************************************************************************
	 * 
	 * Note: We Autowire the repository classes here, because Autowiring the
	 *       service classes would result in cyclic dependencies in 
	 *       project that use the Ignite library
	 *       
	 * Tony De Buys
	 * 16/11/2023
	 * 
	 * ************************************************************************
	 */
	@Autowired
	IndividualRepository individualRepository;

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	HttpServletRequest httpServletRequest;

	public boolean hasPermission(String permissionCode) {
		boolean result = false;
		String username = getUsername();

		if ((permissionCode == null) || (permissionCode.isEmpty())) {
			return result;
		}

		List<Permission> permissions = permissionRepository.getPermissionsForUser(username);

		for (Permission permission : permissions) {
			if (permissionCode.equalsIgnoreCase(permission.getPermissionCode())) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * Get the users name from the security context
	 *
	 * @return The username of the current user
	 */
	public String getUsername() {
		String result = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			result = authentication.getName();
		}

		return result;
	}

	/**
	 * Get the users first and last name, using the security context
	 *
	 * @return The first and last names of the current user
	 */
	public String getDisplayName() {
		String result = getUsername();

		// return anonymous if the user is not logged in
		if ("anonymousUser".equals(result)) {
			return "Anonymous";
		}

		if ((result != null) && (!result.isEmpty())) {
			Individual individual = individualRepository.findByUsername(result);

			if (individual != null) {
				result = individual.getDisplayName();
			}
		}

		return result;
	}

	public Individual getIndividual() {
		Individual result = null;
		String username = getUsername();

		// return anonymous if the user is not logged in
		if ("anonymousUser".equals(username)) {
			return result;
		}

		if ((username != null) && (!username.isEmpty())) {
			result = individualRepository.findByUsername(username);
		}

		return result;
	}

	public String getRole() {
		String result = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			if (authorities.size() > 0) {
				SimpleGrantedAuthority sga = (SimpleGrantedAuthority) authorities.toArray()[0];
				result = sga.getAuthority();
			}
		}

		return result;
	}

	/**
	 * Process a login event for the user in the userAuthentication object
	 *
	 * @param userAuthentication
	 */
	public void loginEvent(UserAuthentication userAuthentication) {
		Individual individual = individualRepository.findByUsername(userAuthentication.getName());

		// TODO: securityUtils not setting last login field

		// If applicationUser is null it means that they're not in our database... we therefore cannot update anything...
		if (individual != null) {
			individual.setLastLoginTimestamp(new Date());

			individualRepository.save(individual);
		}
	}
}
