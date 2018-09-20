package com.application.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.UserDocument;

@Component
public class UserCrudService {
	
	@Autowired
	UsersDao userDao ;
	
	Map<String,String> users = new HashMap<>();
	Set<String> userList = new HashSet<>();
	
	public void save(String uuid, String username) {
		users.put(uuid,username);
	}

	public void add(String username) {
		userList.add(username);
	}
	public void remove(String username) {
		userList.remove(username);
	}
	public List<UserDocument> getAllUsers(){
		return userDao.findConnectedDetails(userList);
	}
	public UserDocument find(String token) {
		return userDao.find(users.get(token));
		
	}

}
