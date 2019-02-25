package com.application.authentication.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

public class JWTAuthenticationService implements AutheticationService{

	private static final String SECRET_KEY = "WaPt1XXXn54C0RnF1AkE4UXY12PWELRNU5I2CS0M3==";
	@Override
	public String generateToken(UserDetails userDetails) {
		Date now = new Date();
			Map<String,Object> header = new HashMap<>(2);
		header.put("alg","HS256");
		header.put("token", "jwt");
		return Jwts.builder()
		.setHeader(header)
		.setSubject(userDetails)
		.claim("type", "user")
		.setIssuedAt(now)
		.signWith(
			    SignatureAlgorithm.HS256,
			    TextCodec.BASE64.encode(SECRET_KEY)
			  ).compact();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> decodeToken(String token) {
		return (Map<String, Object>) Jwts.parser()
		       .setSigningKey(TextCodec.BASE64.encode(SECRET_KEY))
		       .parse(token).getBody();
	}

	
	public static void main(String[] args) {
		JWTAuthenticationService  service = new JWTAuthenticationService();
		String token = service.generateToken("yash");
		
		service.decodeToken(token);
	}

}
