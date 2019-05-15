package com.application.bean;

public class ViewPostBean {
	
	private String usernameForPost;
	
	private User activeUser;
	
	public String getUsernameForPost() {
		return usernameForPost;
	}

	public void setUsernameForPost(String usernameForPost) {
		this.usernameForPost = usernameForPost;
	}

	public User getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(User activeUser) {
		this.activeUser = activeUser;
	}


}
