package net.integrategroup.ignite.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class UserAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1363645407710887992L;

    private final UserDetails userDetails;

    public UserAuthentication(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public UserDetails getPrincipal() {
        return userDetails;
    }

	@Override
	public Object getCredentials() {
		return userDetails.getPassword();
	}

}
