package com.application.bean;

import java.util.List;
import java.util.Set;

public class NotificationBean {

	private String message;
	private String timeAgo;
	private Set<String> pictureUrls;
	private String postId;
	private String type;
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
	public Set<String> getPictureUrls() {
		return pictureUrls;
	}
	public void setPictureUrls(Set<String> pictureUrls) {
		this.pictureUrls = pictureUrls;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
