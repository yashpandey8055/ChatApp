package com.application.service.dao;

import java.util.ArrayList;
import java.util.List;

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
		NotificationDocument document =  template.findOne(Query.query(Criteria.where(key).is(value)), NotificationDocument.class);
		if(document==null) {
			return new NotificationDocument();
		}
		return document;
	}
	
	public NotificationDocument findOneByQuery(Query query) {
		NotificationDocument document =  template.findOne(query, NotificationDocument.class);
		if(document==null) {
			return new NotificationDocument();
		}
		return document;
	}
	
	public List<NotificationDocument> findAll(String key,String value) {
		List<NotificationDocument> document =  template.find(Query.query(Criteria.where(key).is(value)), NotificationDocument.class);
		if(document==null) {
			return new ArrayList<>(1);
		}
		return document;
	}
	public NotificationDocument save(NotificationDocument notification) {
		 template.save(notification);
		 return notification;
		
	}
}
