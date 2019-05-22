package com.application.bean;

public class MessageBean {
	
	private String message;
	private String receiver;
	private String sender;
	private String senderProfileUrl;
	private Integer bucketNumber;
	private Integer pageSize;
	private String conversationId;
	
	
	public MessageBean() {
		/**
		 * Straight Initializing the Bean, 
		 * and Empty constructor
		 */
	}
	
	/**
	 * Cloning another Message Bean 
	 * to instantiate this bean
	 */
	public MessageBean(MessageBean bean) {
		this.message = bean.getMessage();
		this.receiver = bean.getReceiver();
		this.sender = bean.getSender();
		this.senderProfileUrl = bean.getSenderProfileUrl();
		this.bucketNumber = bean.getBucketNumber();
		this.pageSize = bean.getPageSize();
	}
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
	public Integer getBucketNumber() {
		return bucketNumber;
	}
	public void setBucketNumber(Integer bucketNumber) {
		this.bucketNumber = bucketNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}


}
