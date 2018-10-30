package com.application.service.dao.documents;

import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection="users")
public class UserDocument implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonInclude(Include.NON_NULL)
	String id ;

	@JsonInclude(Include.NON_NULL)
	String userName ;
	@JsonInclude(Include.NON_NULL)
	String firstName;
	@JsonInclude(Include.NON_NULL)
	String lastName;
	@JsonInclude(Include.NON_NULL)
	String bio;
	@JsonInclude(Include.NON_NULL)
	String password ;
	@JsonInclude(Include.NON_NULL)
	String profileUrl;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

}