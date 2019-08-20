package com.application.bean;

public class NotificationBean {

	private String sender;
	private String receiver;
	private String notification;
	private String senderProfileUrl;
	private String redirectUrl;
	private String timeAgo;
	private boolean read;
	
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public String getTimeAgo() {
		return timeAgo;
	}
	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getSenderProfileUrl() {
		return senderProfileUrl;
	}
	public void setSenderProfileUrl(String senderProfileUrl) {
		this.senderProfileUrl = senderProfileUrl;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
}
