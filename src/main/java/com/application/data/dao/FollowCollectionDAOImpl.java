package com.application.data.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.application.data.dao.documents.ConnectionsDocument;
import com.application.data.dao.documents.MongoDocument;
import com.mongodb.client.result.DeleteResult;


@Repository
public class FollowCollectionDAOImpl implements IMongoCollection{

	MongoTemplate template;
	
	public FollowCollectionDAOImpl(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public MongoDocument findOne(String key, Object value) {
		return template.findOne(Query.query(Criteria.where(key).is(value)), ConnectionsDocument.class);
	}

	@Override
	public List<? extends MongoDocument> findList(String key, Object value) {
		return template.find(Query.query(Criteria.where(key).is(value)), ConnectionsDocument.class);
	}

	public MongoDocument findWithMutipleKeys(Map<String,Object[]> criterias) {
		Query query=new Query();
		for (Entry<String, Object[]> entry : criterias.entrySet()) { 
			query.addCriteria(Criteria.where(entry.getKey()).all(entry.getValue()));
		}
		return template.findOne(query, ConnectionsDocument.class);
	}

	@Override
	public MongoDocument save(MongoDocument document) {
		template.save(document);
		return document;
	}

	@Override
	public DeleteResult delete(String key, Object value) {
		return template.remove(Query.query(Criteria.where(key).is(value)), ConnectionsDocument.class);
	}

	@Override
	public List<? extends MongoDocument> executeQuery(Query query) {
		return template.find(query, ConnectionsDocument.class);
	}

	@Override
	public Long count(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

}
