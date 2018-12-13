package com.application.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
}
