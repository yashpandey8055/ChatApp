package com.application.bean;

import com.application.service.dao.documents.UserDocument;

public class OnlineNotification {

	private UserDocument user;
	private String action;

	public UserDocument getUser() {
		return user;
	}

	public void setUser(UserDocument user) {
		this.user = user;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	
}
