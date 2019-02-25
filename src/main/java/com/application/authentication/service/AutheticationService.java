package com.application.authentication.service;


import org.springframework.security.core.userdetails.UserDetails;

public interface AutheticationService {

	public String generateToken(UserDetails userDetails);
	
	public UserDetails decodeToken(String token);
	

}
