package com.application.service.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;

import com.application.bean.PostResponse;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.PostDocument;
import com.application.data.dao.documents.UserDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.PostActivityReqResBean;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.GeneralConstants;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.PostService;

public class ImagePostServiceImpl implements PostService{
	
	private MongoTemplate template;
	
	@Autowired
	public ImagePostServiceImpl(MongoTemplate template){
		this.template = template;
	}

	
	@Override
	public GenericResponseBean post(PostActivityReqResBean postActivityReqResBean) {
		PostDocument document = new PostDocument();
		document.setCommentCount(0);
		document.setCreationDate(new Date());
		document.setUpdationDate(new Date());
		document.setIsStatus(false);
		document.setLikes(0);
		document.setPostImageUrl(postActivityReqResBean.getPostImageUrl());
		document.setStatus(postActivityReqResBean.getStatus());
		document.setType(GeneralConstants.IMAGE_POST);
		document.setUserName(postActivityReqResBean.getUserName());
		
		IMongoCollection postCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.POST_DOCUMENT_COLLECTION
				, template);
		postCollection.save(document);
		
		IMongoCollection userCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.USER_DOCUMENT_COLLECTION
				, template);
		
		UserDocument userDocument = (UserDocument)userCollection.findOne(DataAccessObjectConstants.USERNAME, postActivityReqResBean.getUserName());
		
		return PostService.createPostResponse(document,userDocument);
	}

	@Override
	public GenericResponseBean edit(PostActivityReqResBean postActivityReqResBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericResponseBean delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericResponseBean view(String id, String user) {
		// TODO Auto-generated method stub
		return null;
	}

}
