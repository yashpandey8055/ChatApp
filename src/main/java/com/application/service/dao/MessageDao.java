package com.application.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "index"));
		query.limit(1);
		query.addCriteria(Criteria.where("participants").all(user.getParticipants()));
		MessageDocument maxIndex = template.findOne(query, MessageDocument.class);
		user.setIndex(Optional.ofNullable(maxIndex).orElse(new MessageDocument()).getIndex()+1);
		template.save(user);
	}
	
	public List<MessageDocument> getMessages(String user,String receiver,Integer bucket) {
		List<String> participants = new ArrayList<>(2);
		participants.add(user);
		participants.add(receiver);
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "index"));
		query.limit(1);
		query.addCriteria(Criteria.where("participants").all(participants));
		MessageDocument maxIndex = template.findOne(query, MessageDocument.class);
		if(maxIndex==null) {
			return new ArrayList<>(1);
		}
		Query messageQuery = Query.query(Criteria.where("participants").all(participants)
				.and("index").lte(maxIndex.getIndex()-(bucket*10-10))
				.gt(maxIndex.getIndex()-(bucket*10)));
		return template.find(messageQuery, MessageDocument.class);
	}
	
	public List<MessageDocument> getPastConversation(String user) {
		Query messageQuery = Query.query(Criteria.where("participants").in(user));
		return template.find(messageQuery, MessageDocument.class);
	}
}
