package com.application.service.dao.documents;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="notification")
public class NotificationDocument {
	
	private String _id;
	private String postId;
	private long count = 0;
	private String receiver;
	private String message;
	private Set<String> pictureUrl = new HashSet<>(2);
	private Date date;
	private String type;
	private String currentSender;
	private String lastSender;
	private String linkToPost;
	private Boolean read = false;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
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
	public Set<String> getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(Set<String> pictureUrl) {
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
	public Boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
	public String getCurrentSender() {
		return currentSender;
	}
	public void setCurrentSender(String currentSender) {
		this.currentSender = currentSender;
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
