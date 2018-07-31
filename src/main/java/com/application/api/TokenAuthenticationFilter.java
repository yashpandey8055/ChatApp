package com.application.api;

import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStart;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter{
	private static final String BEARER = "Bearer";

	  public TokenAuthenticationFilter(final RequestMatcher requiresAuth) {
	    super(requiresAuth);
	  }

	  @Override
	  public Authentication attemptAuthentication(
	    final HttpServletRequest request,
	    final HttpServletResponse response) throws IOException {
	    String param = request.getHeader(AUTHORIZATION);
	      
	    
	    String token ;
	    if(param==null) {
	    	token = request.getParameter("token");
	    }else {
	    	 token = param.split(" ")[1];
	    }
	    final Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
	    return getAuthenticationManager().authenticate(auth);
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
	    response.sendRedirect("/views/login.html");
	  }
}
