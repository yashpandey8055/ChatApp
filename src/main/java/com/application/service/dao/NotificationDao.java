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
	MongoTemplate template;
	
	public void save(NotificationDocument document) {
		template.save(document);
	}
	
	public NotificationDocument find(String key,String value) {
		Query q =Query.query(Criteria.where(key).is(value));
		return template.findOne(q,NotificationDocument.class);
	}
}
