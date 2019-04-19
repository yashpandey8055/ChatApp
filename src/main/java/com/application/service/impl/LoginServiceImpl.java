package com.application.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.application.authentication.service.AutheticationService;
import com.application.bean.User;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.UserDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.PasswordService;

@Service
public class LoginServiceImpl {
	
	private MongoTemplate template;
	private PasswordService passwordService;
	private AutheticationService authService;
	
	@Autowired
	public LoginServiceImpl(MongoTemplate template,PasswordService passwordService
			,@Qualifier("JWTAuth")AutheticationService authService) {
		this.template = template;
		this.passwordService = passwordService;
		this.authService = authService;
	}
	
	public GenericResponseBean doLogin(String userName, String password) {
		IMongoCollection userCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.USER_DOCUMENT_COLLECTION
				, template);
		UserDocument userDocument = (UserDocument) userCollection.findOne("userName", userName);
		GenericResponseBean responseBean = new GenericResponseBean();
		if(userDocument!=null&&passwordService.match(userDocument.getPassword(), password)) {
			User userDetails = new User();
			userDetails.setId(userDocument.getId());
			userDetails.setUserName(userDetails.getUsername());
			String token = authService.generateToken(userDetails);
			userDetails.setToken(token);
			responseBean.setCode(HttpStatus.OK);
			responseBean.setData(token);
			responseBean.setType(RequestResponseConstant.SUCCESS_RESPONSE);
			return responseBean;
		}
		responseBean.setCode(HttpStatus.NOT_FOUND);
		responseBean.setData(null);
		responseBean.setType(RequestResponseConstant.FAILURE_RESPONSE);
		return responseBean;
	}
	
}
