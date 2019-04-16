package com.application.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.application.data.dao.IMongoCollectionFactory;
import com.application.data.dao.documents.UserDocument;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.UserActivityReqResBean;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.PasswordService;

@Service
public class LoginService {
	
	MongoTemplate template;
	PasswordService passwordService;
	
	@Autowired
	public LoginService(MongoTemplate template,PasswordService passwordService) {
		this.template = template;
		this.passwordService = passwordService;
	}
	
	public GenericResponseBean service(String userName, String password) {
		UserDocument userDocument = (UserDocument) IMongoCollectionFactory.getInstance(UserDocument.class, template).findOne("userName", userName);
		GenericResponseBean responseBean = new GenericResponseBean();
		if(userDocument!=null&&passwordService.match(userDocument.getPassword(), password)) {
			UserActivityReqResBean userActivityRequResBean = new UserActivityReqResBean();
			userActivityRequResBean.setAge(userDocument.getAge());
			userActivityRequResBean.setBio(userDocument.getBio());
			userActivityRequResBean.setCity(userDocument.getCity());
			userActivityRequResBean.setCountry(userDocument.getCountry());
			userActivityRequResBean.setEmail(userDocument.getEmail());
			userActivityRequResBean.setFirstName(userDocument.getFirstName());
			userActivityRequResBean.setGender(userDocument.getGender());
			userActivityRequResBean.setLastName(userDocument.getLastName());
			userActivityRequResBean.setProfileUrl(userDocument.getProfileUrl());
			userActivityRequResBean.setRating(userDocument.getRating());
			userActivityRequResBean.setState(userDocument.getState());
			userActivityRequResBean.setUserName(userDocument.getUsername());
			responseBean.setCode(HttpStatus.OK);
			responseBean.setData(userActivityRequResBean);
			responseBean.setType(RequestResponseConstant.SUCCESS_RESPONSE);
			return responseBean;
		}
		responseBean.setCode(HttpStatus.NOT_FOUND);
		responseBean.setData(null);
		responseBean.setType(RequestResponseConstant.FAILURE_RESPONSE);
		return responseBean;
	}
}
