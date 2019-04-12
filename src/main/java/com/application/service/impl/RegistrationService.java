package com.application.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.application.data.dao.documents.UserDocument;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.UserRegisterReqResBean;


@Component
public class RegistrationService {

	MongoTemplate template;
	
	@Autowired
	public RegistrationService(MongoTemplate template) {
		this.template = template;
	}
	
	public GenericResponseBean service(UserRegisterReqResBean request) {
		UserDocument userDocument = new UserDocument();
		userDocument.setAge(request.getAge());
		userDocument.setBio(request.getBio());
		userDocument.setCity(request.getCity());
		userDocument.setConversationPts(0);
		userDocument.setCountry(request.getCountry());
		userDocument.setEmail(request.getEmail());
		userDocument.setFirstName(request.getFirstName());
		userDocument.setFollowers(0);
		userDocument.setFollowing(new ArrayList<>(1));
		userDocument.setGender(request.getGender());
		userDocument.setLastName(request.getLastName());
		return null;
		
	}
	
}
