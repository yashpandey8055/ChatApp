package com.application;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	 @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth
	            .inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
	                .withUser("yash").password("password").roles("USER");
	        
	        auth
            .inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("kumod").password("password").roles("USER");
	        
	        auth
            .inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("satish").password("password").roles("USER");
	    }
	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
	        http
	            .csrf()
	                .disable()
	            .authorizeRequests()
	                .antMatchers("/css/**", "/fonts/**", "/image/**", "/js/**").permitAll()
	                .anyRequest().authenticated()
	                .and()
	            .formLogin()
	                .defaultSuccessUrl("/");
	                
	                
	             
	    }
	 
	 public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

		    @Override
		    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		        System.out.println("CustomAuthenticationSuccessHandler");
		        HttpSession session = request.getSession();
		        SavedRequest savedReq = (SavedRequest) session.getAttribute(WebAttributes.ACCESS_DENIED_403);
		        if (savedReq == null) {
		            response.sendRedirect(request.getContextPath() + "/");
		        }
		        else {
		            response.sendRedirect(savedReq.getRedirectUrl());
		        }
		    }

		

		}

}
