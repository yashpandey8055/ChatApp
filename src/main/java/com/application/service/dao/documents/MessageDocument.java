package com.application.service.dao.documents;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="messages")
public class MessageDocument {

	private String sender;
	private String receiver ;
	private String message;
	private Timestamp date; 
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
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
}
