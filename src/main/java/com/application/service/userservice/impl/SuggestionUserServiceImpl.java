package com.application.service.userservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.application.data.dao.documents.MongoDocument;
import com.application.data.dao.documents.UserDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.UserSuggestionReqResBean;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.GeneralConstants;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.UserDetailsService;


@Service("SuggestingUserService")
public class SuggestionUserServiceImpl implements UserDetailsService{

	private MongoTemplate template;
	
	@Autowired
	public SuggestionUserServiceImpl(MongoTemplate template) {
		this.template = template;
	}
	
	@Override
	public GenericResponseBean getUserDetails(String regex) {
		List<? extends MongoDocument>  userDocuments = MongoCollectionFactory.getInstance(DataAccessObjectConstants.USER_DOCUMENT_COLLECTION
				, template).executeQuery(Query.query(Criteria.where(DataAccessObjectConstants.USERNAME)
						.regex(regex)));
		GenericResponseBean responseBean = new GenericResponseBean();
		List<UserSuggestionReqResBean> userSuggestions = new ArrayList<>();
		for(MongoDocument document: userDocuments) {
			UserDocument userDocument = (UserDocument) document;
			UserSuggestionReqResBean userSuggestionBean = new UserSuggestionReqResBean();
			userSuggestionBean.setBio(userDocument.getBio());
			userSuggestionBean.setFirstName(userDocument.getFirstName());
			userSuggestionBean.setLastName(userDocument.getLastName());
			userSuggestionBean.setUserName(userDocument.getUsername());
			userSuggestionBean.setProfilePictureUrl(userDocument.getProfileUrl());
			userSuggestionBean.setLinkToProfile(GeneralConstants.USER_REDIRECT_URL+userDocument.getUsername());
			userSuggestions.add(userSuggestionBean);
		}
		if(!userSuggestions.isEmpty()) {
			responseBean.setCode(HttpStatus.OK);
			responseBean.setData(userSuggestions);
			responseBean.setType(RequestResponseConstant.SUCCESS_RESPONSE);
			return responseBean;
		}
		responseBean.setCode(HttpStatus.NOT_FOUND);
		responseBean.setData(null);
		responseBean.setType(RequestResponseConstant.FAILURE_RESPONSE);
		return responseBean;
	}
}
