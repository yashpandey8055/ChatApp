package com.application.service.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.application.service.dao.documents.UserDocument;

@Repository
public class UsersDao {

	@Autowired
	MongoTemplate template;
	
	public void save(UserDocument user) {
		template.save(user);
	}

	public UserDocument find(String userName) {
		Query query = Query.query(Criteria.where("userName").is(userName));
		return template.findOne(query, UserDocument.class);

	}

	public List<UserDocument> findByQuery(Query query) {
		return template.find(query, UserDocument.class);

	}

	public List<UserDocument> findAll(Collection<String> userName) {
		Query query = Query.query(Criteria.where("userName").in(userName));
		query.fields().include("userName").include("firstName").include("lastName").include("bio").include("profileUrl");
		return  template.find(query, UserDocument.class);


	}
	public List<UserDocument> findConnectedDetails(Set<String> list){
		return template.find(Query.query(Criteria.where("_id").in(list)), UserDocument.class);
	}

	public void pushList(List<UserDocument> documents) {
		template.insert(documents, UserDocument.class);
	}
}
