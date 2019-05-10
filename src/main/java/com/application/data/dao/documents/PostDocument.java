package com.application.data.dao.documents;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="posts")
public class PostDocument extends MongoDocument{
	private String _id;
	public String getId() {
		return _id;
	}
	public void setId(String id) {
		this._id = id;
	}
	private String status;
	private long likes;
	private long commentCount; 
	private String username;
	private Boolean isStatus;
	private String type;
	private String postImageUrl;
	private Date creationDate;
	private Date updationDate;

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

	public Boolean isIsStatus() {
		return isStatus;
	}
	public void setIsStatus(Boolean isStatus) {
		this.isStatus = isStatus;
	}
	public String getPostImageUrl() {
		return postImageUrl;
	}
	public void setPostImageUrl(String postImageUrl) {
		this.postImageUrl = postImageUrl;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getUpdationDate() {
		return updationDate;
	}
	public void setUpdationDate(Date updationDate) {
		this.updationDate = updationDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
}
