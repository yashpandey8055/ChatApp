package com.application.authentication.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.application.bean.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtHandlerAdapter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

@Component("JWTAuth")
public class JWTAuthenticationService implements AutheticationService{
	
	private static final Logger LOG = LoggerFactory.getLogger(JWTAuthenticationService.class);
	@Autowired
	ObjectMapper mapper;
	
	private static final String SECRET_KEY = "WaPt1XXXn54C0RnF1AkE4UXY12PWELRNU5I2CS0M3==";
	
	@Override
	public String generateToken(UserDetails userDetails) {
		Date now = new Date();
			Map<String,Object> header = new HashMap<>(2);
		header.put("alg","HS256");
		header.put("token", "jwt");
		return Jwts.builder()
		.setHeader(header)
		.setSubject(userDetails.toString())
		.claim("type", "user")
		.setIssuedAt(now)
		.signWith(
			    SignatureAlgorithm.HS256,
			    TextCodec.BASE64.encode(SECRET_KEY)
			  ).compact();
	}
	
	@Override
	public UserDetails decodeToken(String token) {
		if(token!=null&!token.equalsIgnoreCase("null")) {
		String jwt =   Jwts.parser()
		       .setSigningKey(TextCodec.BASE64.encode(SECRET_KEY))
		       .parse(token,new JwtHandlerAdapter<String>() {
		    	   		@Override
		    	          public String onClaimsJws(Jws<Claims> jws) {
		    	              return jws.getBody().getSubject();
		    	         }
		       });
		try {
			return mapper.readValue(jwt, User.class);
		}catch(IOException ex) {
			LOG.error("Cannot Parse Token subject {}  to instance of User.class",jwt);
		}
		}
		return null;
	}
}
