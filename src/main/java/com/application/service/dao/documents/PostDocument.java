package com.application.service.dao.documents;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="posts")
public class PostDocument {

	private String id;
	private String status;
	private long likes;
	private long commentCount; 
	private String userName;
	private List<String> comments;
	private boolean isStatus;
	private String postImageUrl;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public boolean isStatus() {
		return isStatus;
	}
	public void setIsStatus(boolean isStatus) {
		this.isStatus = isStatus;
	}
	public String getPostImageUrl() {
		return postImageUrl;
	}
	public void setPostImageUrl(String postImageUrl) {
		this.postImageUrl = postImageUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
