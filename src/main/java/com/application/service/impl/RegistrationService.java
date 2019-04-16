package com.application.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.application.data.dao.IMongoCollectionFactory;
import com.application.data.config.DefaultApplicationProperties;
import com.application.data.dao.documents.UserDocument;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.UserRegisterReqResBean;
import com.application.service.PasswordService;


@Service
public class RegistrationService {

	private MongoTemplate template;
	
	private PasswordService passwordService;
	
	private LoginService loginService;
	
	private DefaultApplicationProperties defaultApplicationProperties;
	
	@Autowired
	public RegistrationService(MongoTemplate template,PasswordService passwordService,LoginService loginService) {
		this.template = template;
		this.passwordService = passwordService;
		this.loginService = loginService;
	}
	
	public GenericResponseBean service(UserRegisterReqResBean request) {
		UserDocument userDocument = new UserDocument();
		userDocument.setAge(request.getAge());
		userDocument.setBio(request.getBio());
		userDocument.setCity(request.getCity());
		userDocument.setCountry(request.getCountry());
		userDocument.setEmail(request.getEmail());
		userDocument.setFirstName(request.getFirstName());
		userDocument.setFollowers(0);
		userDocument.setFollowing(new ArrayList<>(1));
		userDocument.setGender(request.getGender());
		userDocument.setLastName(request.getLastName());
		userDocument.setPassword(passwordService.encrypt(request.getPassword()));
		userDocument.setPosts(new ArrayList<>(1));
		userDocument.setProfileUrl(getDefaultApplicationProperties().getDefaultProfileUrl());
		userDocument.setRating(0.0f);
		userDocument.setState(request.getState());
		userDocument.setUsername(request.getUsername());
		IMongoCollectionFactory.getInstance(UserDocument.class, template).save(userDocument);
		return loginService.service(request.getUsername(), request.getPassword());
	}
	
	public DefaultApplicationProperties getDefaultApplicationProperties() {
		return defaultApplicationProperties;
	}

	@Autowired
	public void setDefaultApplicationProperties(DefaultApplicationProperties defaultApplicationProperties) {
		this.defaultApplicationProperties = defaultApplicationProperties;
	}
	
}
