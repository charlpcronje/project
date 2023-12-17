package net.integrategroup.ignite.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import net.integrategroup.ignite.persistence.service.IndividualService;

@Component
public class UserAuthenticationFactory {

	@Autowired
	IndividualService individualService;

	//
	//	@Autowired
	//	RoleService roleService;
	//

	public UserAuthentication create(Authentication authentication) {
		String username = authentication.getName();

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.addAll(authentication.getAuthorities());

		return create(username, authorities);
	}

	public UserAuthentication create(String username) {
		return create(username, null);
	}

	public UserAuthentication create(String username, List<GrantedAuthority> authorities) {
		if (authorities == null) {
			authorities = new ArrayList<>();
		}

		//
		//		List<Role> gdiRoles = roleService.findRolesForUsernameAndDataProvider(username, dataProviderName);
		//		for (Role role : gdiRoles) {
		//			authorities.add(new SimpleGrantedAuthority(role.getRoleDescription()));
		//		}
		//

		User user = new User(username, username, authorities);
		return new UserAuthentication(user);
	}

}
