package com.application.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.application.bean.UserDocument;

@Repository
public class UsersDao {

	@Autowired
	MongoTemplate template;
	
	public void save(UserDocument user) {
		template.save(user);
	}

	public UserDocument find(String userName) {
		return template.findOne(Query.query(Criteria.where("userName").is(userName)), UserDocument.class);

	}
	
	public List<UserDocument> findConnectedDetails(Set<String> list){
		return template.find(Query.query(Criteria.where("userName").in(list)), UserDocument.class);
	}
}
