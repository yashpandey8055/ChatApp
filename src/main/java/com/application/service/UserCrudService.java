package com.application.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.UserDocument;

@Component
public class UserCrudService {
	
	@Autowired
	UsersDao userDao ;
	
	Map<String,String> users = new HashMap<>();
	Map<String,UserDocument> userDetails = new HashMap<>();
	
	public void save(String uuid, String username) {
		users.put(uuid,username);
	}

	public void add(UserDocument userDocument) {
		userDetails.put(userDocument.getUsername(), userDocument);
	}
	public void remove(String username) {
		userDetails.remove(username);
	}
	public Collection<UserDocument> getAllUsers(){
		return userDetails.values();
	}
	public UserDocument findWithToken(String token) {
		return userDao.find(users.get(token));
		
	}
	public UserDocument findWithUsername(String username) {
		return userDetails.get(username);
		
	}
}
