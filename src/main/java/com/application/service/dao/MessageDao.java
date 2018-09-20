package com.application.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.application.service.dao.documents.MessageDocument;

@Repository
public class MessageDao {
	
	
	MongoTemplate template;
	
	@Autowired
	public MessageDao(MongoTemplate template) {
		this.template = template;
	}

	public void save(MessageDocument user) {
		template.save(user);
	}
}
