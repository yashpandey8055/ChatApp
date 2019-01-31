package com.application.service.dao.documents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="notification")
public class NotificationDocument {

	private String postId;
	private long count = 0;
	private String receiver;
	private String message;
	private List<String> pictureUrl = new ArrayList<>(2);
	private Date date;
	private String lastSender;
	private String linkToPost;
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(List<String> pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getLastSender() {
		return lastSender;
	}
	public void setLastSender(String lastSender) {
		this.lastSender = lastSender;
	}
	public String getLinkToPost() {
		return linkToPost;
	}
	public void setLinkToPost(String linkToPost) {
		this.linkToPost = linkToPost;
	}
}
