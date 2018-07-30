package com.application.bean;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

@Value
public class User implements UserDetails{

	private static final long serialVersionUID = 2396654715019746670L;

	
	String id;
	  String username;
	  String password;

	  @JsonCreator
	  User(@JsonProperty("id") final String id,
	       @JsonProperty("username") final String username,
	       @JsonProperty("password") final String password) {
	    super();
	    this.id = requireNonNull(id);
	    this.username = requireNonNull(username);
	    this.password = requireNonNull(password);
	  }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return new ArrayList<>();
	}
	 
	public String getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}
	
	public static Builder builder() {
		return new Builder();
		
	}
	public static class Builder {
			String id;
		  String username;
		  String password;
		  
		  

	    public Builder id(String age) {
	      this.id = age;
	      return this;
	    }

	    public Builder username(String phone) {
	      this.username = phone;
	      return this;
	    }

	    public Builder password(String address) {
	      this.password = address;
	      return this;
	    }

	    public User build() {
	    	 			return new User(id, username,password);
	    	  		}

	  }

}
