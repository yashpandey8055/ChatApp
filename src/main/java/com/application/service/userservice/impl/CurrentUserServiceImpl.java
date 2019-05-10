package com.application.service.userservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.application.data.dao.documents.UserDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.UserActivityReqResBean;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.PasswordService;
import com.application.service.UserDetailsService;

@Service("currentUserService")
public class CurrentUserServiceImpl implements UserDetailsService {
	private MongoTemplate template;
	PasswordService passwordService;
	
	@Autowired
	public CurrentUserServiceImpl(MongoTemplate template,PasswordService passwordService) {
		this.template = template;
		this.passwordService = passwordService;
		}
	
	@Override
	public GenericResponseBean getUserDetails(String username) {
		UserDocument userDocument = (UserDocument) MongoCollectionFactory.getInstance(DataAccessObjectConstants.USER_DOCUMENT_COLLECTION
				, template).findOne("username", username);
		GenericResponseBean responseBean = new GenericResponseBean();
		if(userDocument!=null) {
			UserActivityReqResBean userActivityRequResBean = new UserActivityReqResBean();
			userActivityRequResBean.setAge(userDocument.getAge());
			userActivityRequResBean.setBio(userDocument.getBio());
			userActivityRequResBean.setCity(userDocument.getCity());
			userActivityRequResBean.setCountry(userDocument.getCountry());
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
