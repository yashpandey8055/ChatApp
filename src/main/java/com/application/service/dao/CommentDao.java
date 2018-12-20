package com.application.service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.application.service.dao.documents.CommentDocument;

@Repository
public class CommentDao {

	@Autowired
	MongoTemplate template;
	
	public String insert(CommentDocument document) {
		template.save(document);
		return  document.getId();
	}
	
	public List<CommentDocument> find(String postId) {
		return template.find(Query.query(Criteria.where("postId").is(postId)).limit(3), CommentDocument.class);
	}
}
