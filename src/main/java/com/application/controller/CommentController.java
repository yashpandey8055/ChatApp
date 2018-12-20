package com.application.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.CommentInsertBean;
import com.application.bean.PostResponse;
import com.application.service.dao.CommentDao;
import com.application.service.dao.PostDao;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.CommentDocument;
import com.application.service.dao.documents.PostDocument;
import com.application.service.dao.documents.UserDocument;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	PostDao postDao;
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	UsersDao userDao;

	@PostMapping("/insert")
	public ResponseEntity<CommentDocument> insertPost(@AuthenticationPrincipal UserDocument currentUser, @RequestBody CommentInsertBean comment){
		PostDocument post = postDao.findOnes(comment.getPostId());
		CommentDocument commentDocument = new CommentDocument();
		commentDocument.setLikes(0);
		commentDocument.setReplies(new ArrayList<>()); 
		commentDocument.setRepliesCount(0);
		commentDocument.setMessage(comment.getComment());
		commentDocument.setUserName(comment.getUserName());
		commentDocument.setProfileUrl(currentUser.getProfileUrl());
		commentDocument.setPostId(post.getId());
		commentDao.insert(commentDocument);
		post.getComments().add(commentDao.insert(commentDocument));
		post.setComments(post.getComments());
		postDao.insert(post);
		return new ResponseEntity<>(commentDocument,HttpStatus.OK);
		
	}
}
