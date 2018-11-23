package com.application.service.dao.documents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="messages")
public class MessageDocument implements Comparable<MessageDocument>{

	private String sender;
	private List<String> participants = new ArrayList<>(2);
	private String receiver ;
	private String message;
	private Date date; 
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		participants.add(sender);
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		participants.add(receiver);
		this.receiver = receiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
	@Override
	public int compareTo(MessageDocument o) {
		return getDate().compareTo(o.getDate());
	}

}
