package com.application.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.application.authentication.service.AutheticationService;

@Component
public class TokenAutheticationProvider extends AbstractUserDetailsAuthenticationProvider{

	@Autowired
	@Qualifier("JWTAuth")
	AutheticationService auth;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) {
		/**
		 * No Additional Checks Required
		 */
	}

	@Override
	protected UserDetails retrieveUser(String token, UsernamePasswordAuthenticationToken authentication){
		return auth.decodeToken(token);
	}

}
