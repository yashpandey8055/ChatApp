package com.application.data.dao.documents;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="comments")
public class CommentDocument extends MongoDocument{

	private String postId;
	private String userName;
	private String profileUrl;
	private String message;
	private List<String> replies;
	private boolean likedByUser;
	private long likes;
	private String daysAgo;
	private Boolean isStatus;
	private long repliesCount;
	private Date creationDate;
	private Date updationDate;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getReplies() {
		return replies;
	}
	public void setReplies(List<String> replies) {
		this.replies = replies;
	}
	public long getLikes() {
		return likes;
	}
	public void setLikes(long likes) {
		this.likes = likes;
	}
	public long getRepliesCount() {
		return repliesCount;
	}
	public void setRepliesCount(long repliesCount) {
		this.repliesCount = repliesCount;
	}
	public boolean isLikedByUser() {
		return likedByUser;
	}
	public void setLikedByUser(boolean likedByUser) {
		this.likedByUser = likedByUser;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public Boolean isIsStatus() {
		return isStatus;
	}
	public void setIsStatus(Boolean isStatus) {
		this.isStatus = isStatus;
	}
	public String getDaysAgo() {
		return daysAgo;
	}
	public void setDaysAgo(String daysAgo) {
		this.daysAgo = daysAgo;
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
	
}
