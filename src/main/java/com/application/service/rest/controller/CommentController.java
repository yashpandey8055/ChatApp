package com.application.service.rest.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.CommentInsertBean;
import com.application.bean.User;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.CommentDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.RequestResponseConstant;


@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	MongoTemplate template;

	@PostMapping("/insert")
	public ResponseEntity<GenericResponseBean> insertComment(@AuthenticationPrincipal User currentUser, @RequestBody CommentInsertBean comment){
		IMongoCollection commentCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.COMMENT_DOCUMENT_COLLECTION
				, template);
		CommentDocument commentDocument = new CommentDocument();
		commentDocument.setLikes(0);
		commentDocument.setReplies(new ArrayList<>()); 
		commentDocument.setRepliesCount(0);
		commentDocument.setMessage(comment.getComment());
		commentDocument.setUserName(currentUser.getUsername());
		commentDocument.setProfileUrl(currentUser.getProfileUrl());
		commentDocument.setPostId(comment.getPostId());
		commentDocument.setCreationDate(new Date());
		commentDocument.setUpdationDate(new Date());
		commentCollection.save(commentDocument);
		GenericResponseBean responseBean = new GenericResponseBean();
		responseBean.setCode(HttpStatus.OK);
		responseBean.setType(RequestResponseConstant.SUCCESS_RESPONSE);
		responseBean.setData(commentDocument);
		return new ResponseEntity<>(responseBean,HttpStatus.OK);
		
	}
	
}
