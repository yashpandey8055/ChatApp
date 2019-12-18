package com.application.data.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.application.data.dao.documents.LikeDocument;
import com.application.data.dao.documents.MongoDocument;
import com.mongodb.client.result.DeleteResult;



@Repository
public class LikesCollectionDAOImpl implements IMongoCollection {

	MongoTemplate template;
	
	public LikesCollectionDAOImpl(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public MongoDocument findOne(String key, Object value) {
		return template.findOne(Query.query(Criteria.where(key).is(value)), LikeDocument.class);
	}

	@Override
	public List<LikeDocument> findList(String key, Object value) {
		return template.find(Query.query(Criteria.where(key).is(value)), LikeDocument.class);
	}

	@Override
	public MongoDocument save(MongoDocument document) {
		template.save(document);
		return document;
	}

	@Override
	public DeleteResult delete(String key, Object value) {
		return template.remove(Query.query(Criteria.where(key).is(value)), LikeDocument.class);
	}

	@Override
	public List<LikeDocument> executeQuery(Query query) {
		return template.find(query, LikeDocument.class);
	}

	@Override
	public Long count(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

}
