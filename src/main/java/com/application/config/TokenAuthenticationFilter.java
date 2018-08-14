package com.application.config;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	protected TokenAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws  IOException, ServletException {
		String param = request.getHeader(AUTHORIZATION);
	    String token ;
	    if(param==null) {
	    	token = request.getParameter("token").replaceAll("\"","");
	    }else {
	    	token = param.split(" ")[1].replaceAll("\"","");
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
