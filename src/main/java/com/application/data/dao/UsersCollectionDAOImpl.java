package com.application.data.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.application.data.dao.documents.UserDocument;
import com.mongodb.client.result.DeleteResult;


@Repository
public class  UsersCollectionDAOImpl implements MongoCollectionDAO<UserDocument>{

	@Autowired
	AbstractMongoCollectionFactory<UsersCollectionDAOImpl,UserDocument> factory;
	
	private MongoTemplate template;
	
	@PostConstruct
	public void init() {
		
	}
	@Autowired
	public UsersCollectionDAOImpl(MongoTemplate template){
		this.template = template;
	}

	@Override
	public UserDocument findOne(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDocument> findList(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDocument insert(UserDocument document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDocument update(UserDocument document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeleteResult delete(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
