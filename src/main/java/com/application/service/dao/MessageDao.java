package com.application.service.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
	
	public List<MessageDocument> getMessages(String user,String receiver) {
		List<String> participants = new ArrayList<>(2);
		participants.add(user);
		participants.add(receiver);
		Query messageQuery = Query.query(Criteria.where("participants").all(participants));
		return template.find(messageQuery, MessageDocument.class);
	}
	
	public List<MessageDocument> getPastConversation(String user) {
		Query messageQuery = Query.query(Criteria.where("participants").in(user));
		return template.find(messageQuery, MessageDocument.class);
	}
}
