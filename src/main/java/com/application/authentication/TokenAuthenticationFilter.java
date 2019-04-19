package com.application.authentication;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class TokenAuthenticationFilter  extends AbstractAuthenticationProcessingFilter{

	public TokenAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}
	
	private static final String TOKEN = "token";

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
		

	    final Authentication auth = new UsernamePasswordAuthenticationToken(extractToken(request), extractToken(request));
	    try {
	    	if(auth.getPrincipal()==null) {
	    		throw new IllegalArgumentException();
	    	}
	    	return getAuthenticationManager().authenticate(auth);
	    }catch(IllegalArgumentException e) {
	    	throw new AuthenticationException(auth.getName()+" is not Autheticated") {
				private static final long serialVersionUID = 1L;
	    		
	    	};
	    }
	}
	
	@Override
	  protected void successfulAuthentication(
	    final HttpServletRequest request,
	    final HttpServletResponse response,
	    final FilterChain chain,
	    final Authentication authResult) throws IOException, ServletException {
	    super.successfulAuthentication(request, response, chain, authResult);
	    chain.doFilter(request, response);
	  }
	  @Override
	  protected void unsuccessfulAuthentication(HttpServletRequest request,
				HttpServletResponse response, AuthenticationException failed)
				throws IOException, ServletException {
	    response.sendRedirect("/secure/login");
	  }
	  
	  private String extractToken(HttpServletRequest request) {
		  String param = request.getHeader(AUTHORIZATION);
		    String token =null;
			Cookie[] cookies = request.getCookies();
			if(cookies!=null) {
			for(Cookie cookie:cookies) {
				if(TOKEN.equalsIgnoreCase(cookie.getName())) {
					token = cookie.getValue();
				}
			}
			}
		    if(param==null) {
		    	if(request.getParameter(TOKEN)!=null) {
		    		token = request.getParameter(TOKEN).replaceAll("\"","");
		    	}
		    }else {
		    	token = param.split(" ")[1].replaceAll("\"","");
		    }
		    return token;
	  }

}
