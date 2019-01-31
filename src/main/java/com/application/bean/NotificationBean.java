package com.application.bean;

import java.util.List;

public class NotificationBean {

	private String message;
	private String timeAgo;
	private List<String> pictureUrls;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimeAgo() {
		return timeAgo;
	}
	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}
	public List<String> getPictureUrls() {
		return pictureUrls;
	}
	public void setPictureUrls(List<String> pictureUrls) {
		this.pictureUrls = pictureUrls;
	}
}
