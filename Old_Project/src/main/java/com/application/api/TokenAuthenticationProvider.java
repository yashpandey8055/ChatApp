package com.application.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{
	  @Autowired
	  UserAuthenticationService auth;
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// Nothing to do
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		final Object token = authentication.getCredentials();
	    return Optional
	      .ofNullable(token)
	      .map(String::valueOf)
	      .flatMap(auth::findByToken)
	      .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
	}

}
