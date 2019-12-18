package com.application.data.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.application.data.dao.documents.MongoDocument;
import com.application.data.dao.documents.ConversationDocument;
import com.mongodb.client.result.DeleteResult;

@Repository
public class ConversationCollectionDAOImpl implements IMongoCollection {

	MongoTemplate template;
	@Autowired
	public ConversationCollectionDAOImpl(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public MongoDocument findOne(String key, Object value) {
		return template.findOne(Query.query(Criteria.where(key).is(value)), ConversationDocument.class);
	}

	@Override
	public List<ConversationDocument> findList(String key, Object value) {
		return template.find(Query.query(Criteria.where(key).is(value)), ConversationDocument.class);
	}

	@Override
	public MongoDocument save(MongoDocument document) {
		template.save(document);
		return document;
	}

	@Override
	public DeleteResult delete(String key, Object value) {
		return template.remove(Query.query(Criteria.where(key).is(value)), ConversationDocument.class);
	}

	@Override
	public List<ConversationDocument> executeQuery(Query query) {
		return template.find(query, ConversationDocument.class);
	}

	@Override
	public Long count(Query query) {
		return template.count(query, ConversationDocument.class);
	}

}
