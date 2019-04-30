package com.application.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.application.bean.PostResponse;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.LikeDocument;
import com.application.data.dao.documents.MongoDocument;
import com.application.data.dao.documents.PostDocument;
import com.application.data.dao.documents.UserDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.PostActivityReqResBean;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.GeneralConstants;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.PostService;
import com.mongodb.client.result.DeleteResult;

@Component("StatusPost")
public class StatusPostServiceImpl implements PostService{

	private MongoTemplate template;
	
	@Autowired
	public StatusPostServiceImpl(MongoTemplate template){
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
		document.setPostImageUrl(null);
		document.setStatus(postActivityReqResBean.getStatus());
		document.setType(GeneralConstants.STATUS_POST);
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
		
		return null;
	}

	@Override
	public GenericResponseBean delete(String id) {
		IMongoCollection postCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.POST_DOCUMENT_COLLECTION
				, template);
		DeleteResult result = postCollection.delete(DataAccessObjectConstants.POST_ID, id);
		GenericResponseBean responseBean = new GenericResponseBean();
		responseBean.setCode(HttpStatus.OK);
		responseBean.setType(RequestResponseConstant.SUCCESS_RESPONSE);
		responseBean.setData(result);
		return responseBean;
	}

	@Override
	public GenericResponseBean view(String id,String user) {
		IMongoCollection postCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.POST_DOCUMENT_COLLECTION
				, template);
		IMongoCollection userCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.USER_DOCUMENT_COLLECTION
				, template);
		IMongoCollection commentCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.COMMENT_DOCUMENT_COLLECTION
				, template);
		IMongoCollection likesCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.LIKE_DOCUMENT_COLLECTION
				, template);
		PostDocument post = (PostDocument)postCollection.findOne(DataAccessObjectConstants.ID_FIELD, id);
		GenericResponseBean responseBean = new GenericResponseBean();
		PostResponse postResponse = new PostResponse();
		List<? extends MongoDocument> comments = commentCollection.executeQuery(Query.
				query(Criteria.where(DataAccessObjectConstants.POST_ID).is(id)).limit(GeneralConstants.COMMENT_LIMIT_ON_VIEW_POST));
		postResponse.setUser((UserDocument)userCollection.findOne(DataAccessObjectConstants.USERNAME, post.getUserName()));
		postResponse.setPost(post);
		postResponse.setComments(comments);
		postResponse.setLikesCount(likesCollection.count(Query.
				query(Criteria.where(DataAccessObjectConstants.POST_ID).is(id))));
		postResponse.setLikedByUser(((LikeDocument)likesCollection.findOne(
				DataAccessObjectConstants.POST_ID,id)).getLikedBy().contains(user));
		postResponse.setDaysAgo(GeneralConstants.NOW_INSTANT);
		responseBean.setCode(HttpStatus.OK);
		responseBean.setType(RequestResponseConstant.SUCCESS_RESPONSE);
		responseBean.setData(postResponse);
		return responseBean;
	}


	
}
