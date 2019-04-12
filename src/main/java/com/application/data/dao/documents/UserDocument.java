package com.application.data.dao.documents;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class UserDocument extends MongoDocument{

	private String userName ;
	
	private String firstName;
	
	private String lastName;
	
	private String bio;
	
	private String password ;
	
	private String profileUrl;
	
	private long conversationPts;
	
	private long followers;
	
	private double rating;
	
	private String gender;
	
	private int age;
	
	private List<String> following ;
	
	private Date dateOfBirth;

	private long phoneNumber;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String email;

	private List<String> posts;

	public String getPassword() {
		
		return password;
	}

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
	
	public String getUserName() {
		return userName;
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
	public void setFollowers(long followers) {
		this.followers = followers;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public double getRating() {
		return rating;
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

	public List<String> getPosts() {
		return posts;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setPosts(List<String> posts) {
		this.posts = posts;
	}
	
}
