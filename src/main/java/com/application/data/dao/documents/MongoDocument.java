package com.application.data.dao.documents;

import org.springframework.data.mongodb.core.mapping.Field;

public class MongoDocument {
	@Field("_id")
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
