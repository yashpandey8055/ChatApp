package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.UserDocument;

@RestController
@RequestMapping("/public")
public class UserController {
	
	@Autowired
	UsersDao dao;
	
	public ResponseEntity<UserDocument> followUser(@AuthenticationPrincipal final UserDocument currentUser,String userName){
		UserDocument currentUserDocument = dao.find(currentUser.getUsername());
		UserDocument followedUserDocument = dao.find(userName);
		currentUserDocument.getFollowing().add(userName);
		followedUserDocument.setFollowers(followedUserDocument.getFollowers()+1);
		dao.save(currentUserDocument);
		dao.save(followedUserDocument);
		return new ResponseEntity<>(followedUserDocument,HttpStatus.ACCEPTED);
		
	}
	
}
