package com.application.service.dao.documents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection="users")
public class UserDocument implements UserDetails{

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
	@JsonInclude(Include.NON_NULL)
	long conversationPts;
	@JsonInclude(Include.NON_NULL)
	long followers;
	@JsonInclude(Include.NON_NULL)
	long approvals;
	@JsonInclude(Include.NON_NULL)
	long disapprovals;
	@JsonInclude(Include.NON_NULL)
	String gender;
	@JsonInclude(Include.NON_NULL)
	String dob;
	@JsonInclude(Include.NON_NULL)
	int age;
	@JsonInclude(Include.NON_NULL)
	private List<String> following = new ArrayList<>(1);
	@JsonInclude(Include.NON_NULL)
	int yearOfBirth;
	@JsonInclude(Include.NON_NULL)
	long phoneNumber;
	@JsonInclude(Include.NON_NULL)
	private boolean isFollowing;
	@JsonInclude(Include.NON_NULL)
	String city;
	@JsonInclude(Include.NON_NULL)
	String state;
	@JsonInclude(Include.NON_NULL)
	String country;
	
	@JsonInclude(Include.NON_NULL)
	String email;

	private List<String> posts = new ArrayList<>(1);
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
	public long getConversationPts() {
		return conversationPts;
	}
	public void setConversationPts(long conversationPts) {
		this.conversationPts = conversationPts;
	}
	public long getFollowers() {
		return followers;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public void setFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}
	public void setFollowers(long followers) {
		this.followers = followers;
	}
	public long getApprovals() {
		return approvals;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setApprovals(long approvals) {
		this.approvals = approvals;
	}
	public long getDisapprovals() {
		return disapprovals;
	}
	public boolean isFollowing() {
		return isFollowing;
	}
	public void setIsFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}
	public void setDisapprovals(long disapprovals) {
		this.disapprovals = disapprovals;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<String> getFollowing() {
		return following;
	}
	public void setFollowing(List<String> following) {
		this.following = following;
	}
	public String getUserName() {
		return userName;
	}
	public List<String> getPosts() {
		return posts;
	}
	public int getYearOfBirth() {
		return yearOfBirth;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
	public void setPosts(List<String> posts) {
		this.posts = posts;
	}
}
