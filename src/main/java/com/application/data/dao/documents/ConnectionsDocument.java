package com.application.data.dao.documents;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection="connections")
public class ConnectionsDocument  extends MongoDocument{
	@Id
	@JsonIgnore
	private String id;
	
	private List<String> connection;
	
	private String requester;
	
	private String requestedTo;
	
	private boolean connectiveActive;
	
	private boolean accepted;
	
	private String requesterProfileUrl;

	public boolean isConnectiveActive() {
		return connectiveActive;
	}

	public void setConnectiveActive(boolean connectiveActive) {
		this.connectiveActive = connectiveActive;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getRequestedTo() {
		return requestedTo;
	}

	public void setRequestedTo(String requestedTo) {
		this.requestedTo = requestedTo;
	}

	public List<String> getConnection() {
		return connection;
	}

	public void setConnection(List<String> connection) {
		this.connection = connection;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public String getRequesterProfileUrl() {
		return requesterProfileUrl;
	}

	public void setRequesterProfileUrl(String requesterProfileUrl) {
		this.requesterProfileUrl = requesterProfileUrl;
	}
	
}
