package com.application.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.application.service.dao.documents.NotificationDocument;

@Repository
public class NotificationDao {
	
	@Autowired
	private MongoTemplate template;
	
	public NotificationDocument find(String key,String value) {
		return template.findOne(Query.query(Criteria.where(key).is(value)), NotificationDocument.class);
		
	}
	
	public NotificationDocument save(NotificationDocument notification) {
		 template.save(notification);
		 return notification;
		
	}
}
