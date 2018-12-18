package com.application.service.dao.documents;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="comments")
public class CommentDocument {
	private String id;
	private String message;
	private List<String> replies;
	private long likes;
	private long repliesCount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
}