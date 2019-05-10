package com.application.request.response.bean;


public class PostActivityReqResBean {
	private String id;
	private String status;
	private long likes;
	private long commentCount; 
	private String userName;
	private Boolean isStatus;
	private String type;
	private String postImageUrl;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getLikes() {
		return likes;
	}
	public void setLikes(long likes) {
		this.likes = likes;
	}
	public long getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getIsStatus() {
		return isStatus;
	}
	public void setIsStatus(Boolean isStatus) {
		this.isStatus = isStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPostImageUrl() {
		return postImageUrl;
	}
	public void setPostImageUrl(String postImageUrl) {
		this.postImageUrl = postImageUrl;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
