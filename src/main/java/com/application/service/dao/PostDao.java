package com.application.service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.application.service.dao.documents.PostDocument;

@Repository
public class PostDao {

	@Autowired
	MongoTemplate template;
	
	public String insert(PostDocument document) {
		template.save(document);
		return document.getId();
	}
	
	public PostDocument findOne(String key,String value) {
		return template.findOne(Query.query(Criteria.where(key).is(value)), PostDocument.class);
	}
	
	public PostDocument findList(String key,String value) {
		return template.findOne(Query.query(Criteria.where(key).is(value)), PostDocument.class);
	}
	public List<PostDocument> findByQuery(Query query) {
		return template.find(query, PostDocument.class);
	}

}
