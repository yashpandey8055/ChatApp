package com.application.config;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	private TokenAutheticationProvider provider;

	private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
		    new AntPathRequestMatcher("/public/**"),
		    new AntPathRequestMatcher("/views/**"),
		    new AntPathRequestMatcher("/webjars/**"),
		    new AntPathRequestMatcher("/favicon.ico"),
		    new AntPathRequestMatcher("/")
		    );

	private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);
	 WebSecurityConfig(TokenAutheticationProvider provider){
		super();
		this.provider = provider;
	}
	
	  @Bean
	  TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
	    final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(PROTECTED_URLS);
	    filter.setAuthenticationManager(authenticationManager());
	    filter.setAuthenticationSuccessHandler(successHandler());
	    return filter;
	  }
	 
	  
	  @Override
		public void configure(WebSecurity web) {
			web.ignoring().requestMatchers(PUBLIC_URLS);
		}
	  
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http
		      .sessionManagement()
		      .sessionCreationPolicy(STATELESS)
		      .and()
		      .exceptionHandling()
		      // this entry point handles when you request a protected page and you are not yet
		      // authenticated
		      .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(),PROTECTED_URLS)
		      .and()
		      .authenticationProvider(provider)
		      .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
		      .authorizeRequests()
		      .anyRequest()
		      .authenticated()
		      .and()
		      .csrf().disable()
		      .formLogin().loginPage("/views/login.html").permitAll().and()
		      .httpBasic().disable()
		      .logout().disable();
			}
	
	  @Bean
	  SimpleUrlAuthenticationSuccessHandler successHandler() {
	    final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
	    successHandler.setRedirectStrategy(new NoRedirectStrategy());
	    return successHandler;
	  }
	  
	  /**
	   * Disable Spring boot automatic filter registration.
	   */
	  @Bean
	  FilterRegistrationBean<TokenAuthenticationFilter> disableAutoRegistration(final TokenAuthenticationFilter filter) {
	    final FilterRegistrationBean<TokenAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
	    registration.setEnabled(false);
	    return registration;
	  }

	  @Bean
	  AuthenticationEntryPoint forbiddenEntryPoint() {
	    return new HttpStatusEntryPoint(FORBIDDEN);
	  }
}
