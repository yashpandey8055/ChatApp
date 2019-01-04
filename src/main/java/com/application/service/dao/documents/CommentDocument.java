package com.application.service.dao.documents;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="comments")
public class CommentDocument {
	private String id;
	private String postId;
	private String userName;
	private String profileUrl;
	private String message;
	private List<String> replies;
	private long likes;
	private Date date;
	private String daysAgo;
	private Boolean isStatus;
	private long repliesCount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDaysAgo() {
		return daysAgo;
	}
	public void setDaysAgo(String daysAgo) {
		this.daysAgo = daysAgo;
	}
	
}
