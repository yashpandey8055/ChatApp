package com.application.request.response.bean;

import java.util.Date;

public class PastConversations {
	private String message;
	private Date date;
	private String sender;
	private String profileUrl;
	private String daysAgo;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getDaysAgo() {
		return daysAgo;
	}
	public void setDaysAgo(String daysAgo) {
		this.daysAgo = daysAgo;
}
}
