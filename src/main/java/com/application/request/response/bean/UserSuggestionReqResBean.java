package com.application.request.response.bean;

public class UserSuggestionReqResBean {
	private String userName ;
	private String firstName;
	private String lastName;
	private String bio;
	private String profilePictureUrl;
	private String linkToProfile;
	public String getUserName() {
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
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}
	public String getLinkToProfile() {
		return linkToProfile;
	}
	public void setLinkToProfile(String linkToProfile) {
		this.linkToProfile = linkToProfile;
	}

}
