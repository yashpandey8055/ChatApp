package com.application.bean;

public class MessageBean {

	
	private String message;
	private String receiver;
	private String sender;
	private String senderProfileUrl;
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
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
	public String getSenderProfileUrl() {
		return senderProfileUrl;
	}
	public void setSenderProfileUrl(String senderProfileUrl) {
		this.senderProfileUrl = senderProfileUrl;
	}
	
	public void setCommentNotification(String sender,String message) {
		setMessage(sender+" commented on your post \""+message+"\"");
	}
	
	public void setLikeNotification(String sender) {
		setMessage(sender+" liked your post");
	}
	
	public void setCommentLikeNotification(String sender) {
		setMessage(sender+" liked your comment");
	}
}


