package com.application.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.UserDocument;

@Component
public class OnlineUserPersistenceService {
	
	@Autowired
	UsersDao userDao;
	
	List<String> userDetails = new ArrayList<>();

	public synchronized  void add(UserDocument userDocument) {
		userDetails.add(userDocument.getUsername());
	}
	public synchronized UserDocument remove(String userName) {
		userDetails.remove(userName);
		return userDao.find(userName);
	}
	public Collection<UserDocument> getAllUsers(){
		return userDao.findAll(userDetails);
	}
	public UserDocument findWithUsername(String username) {
		return userDao.find(username);
		
	}
}
