package com.application.data.dao.documents;

import java.util.Comparator;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="notification")
public class NotificationDocument extends MongoDocument implements Comparator<NotificationDocument> {
	@Field("_id")
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String postId;
	private long count = 0;
	private String receiver;
	private String message;
	private Date date;
	private String currentSender;
	private String redirectUrl;
	private Boolean read = false;
	private String pictureUrl;

	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
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
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	@Override
	public int compare(NotificationDocument o1, NotificationDocument o2) {
		if(o1.getDate().after(o2.getDate())) {
			return 1;
		}
		if(o1.getDate().before(o2.getDate())) {
			return -1;
		}
		return 0;
	}
	
}
