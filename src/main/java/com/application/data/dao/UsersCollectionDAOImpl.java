package com.application.data.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.application.data.dao.documents.UserDocument;
import com.mongodb.client.result.DeleteResult;


@Repository
public class UsersCollectionDAOImpl implements MongoCollectionDAO<UserDocument>{

	private MongoTemplate template;

	public UsersCollectionDAOImpl(MongoTemplate template){
		this.template = template;
	}
	@Override
	public <T> UserDocument findOne(String key, T value) {
		return template.findOne(Query.query(Criteria.where(key).is(value)), UserDocument.class);
	}

	@Override
	public <T> List<UserDocument> findList(String key, T value) {
		return template.find(Query.query(Criteria.where(key).is(value)), UserDocument.class);
	}

	@Override
	public UserDocument insert(UserDocument document) {
		template.insert(document);
		return document;
	}

	@Override
	public UserDocument update(UserDocument documents) {
		template.save(documents);
		return documents;
	}

	@Override
	public <T> DeleteResult delete(String key, T value) {
		return template.remove(Query.query(Criteria.where(key).is(value)), UserDocument.class);
	}
	
}
