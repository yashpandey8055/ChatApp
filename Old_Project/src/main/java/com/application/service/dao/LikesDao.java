package com.application.service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.application.service.dao.documents.LikeDocument;


@Repository
public class LikesDao {
	
	
	MongoTemplate template;
	
	@Autowired
	public LikesDao(MongoTemplate template) {
		this.template = template;
	}
	public LikeDocument getLikePost(String postId) {
		Query query = Query.query(Criteria.where("postId").is(postId));
		return template.findOne(query, LikeDocument.class);
	}
	public List<LikeDocument> getLikesPost(String postId) {
		Query query = Query.query(Criteria.where("postId").is(postId));
		return template.find(query, LikeDocument.class);
	}
	public LikeDocument getLikePostByQuery(Query query) {
		return template.findOne(query, LikeDocument.class);
	}
	
	public void saveLikePost(LikeDocument document) {
		template.save(document);
	}
}
