package com.application.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.application.bean.UserDocument;

@Component
public class UserCrudService {
	
	@Autowired
	UsersDao userDao ;
	
	Map<String,String> users = new HashMap<>();
	
	public void save(String uuid, String username) {
		users.put(uuid,username);
		
	}

	public UserDocument find(String token) {
		return userDao.find(users.get(token));
		
	}

}
