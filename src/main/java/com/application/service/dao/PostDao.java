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
		return  document.getId();
	}
	
	
	public PostDocument findOnes(String id) {
		return template.findOne(Query.query(Criteria.where("_id").is(id)), PostDocument.class);
	}
	
	public List<PostDocument> findByOneUserName(String userName) {
		return template.find(Query.query(Criteria.where("userName").is(userName)).limit(10), PostDocument.class);
	}
	public List<PostDocument> findByUserNames(List<String> userNames) {
		return template.find(Query.query(Criteria.where("userName").in(userNames)), PostDocument.class);
	}
}
