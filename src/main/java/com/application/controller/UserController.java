package com.application.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.config.UUIDAuthenticationService;
import com.application.service.UserCrudService;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.UserDocument;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UsersDao dao;
	
	@Autowired
	UserCrudService userService;
	
	UUIDAuthenticationService authentication;
	
	@GetMapping("/follow/{user}")
	public ResponseEntity<UserDocument> followUser(@AuthenticationPrincipal final UserDocument currentUser,@PathVariable("user") String userName){
		UserDocument currentUserDocument = dao.find(currentUser.getUsername());
		UserDocument followedUserDocument = dao.find(userName);
		currentUserDocument.getFollowing().add(userName);
		currentUserDocument.setFollowing(currentUserDocument.getFollowing());
		followedUserDocument.setFollowers(followedUserDocument.getFollowers()+1);
		dao.save(currentUserDocument);
		dao.save(followedUserDocument);
		return new ResponseEntity<>(followedUserDocument,HttpStatus.ACCEPTED);
		
	}

	@GetMapping("/unfollow/{user}")
	public ResponseEntity<UserDocument> unfollowUser(@AuthenticationPrincipal final UserDocument currentUser,@PathVariable("user") String userName){
		UserDocument currentUserDocument = dao.find(currentUser.getUsername());
		UserDocument followedUserDocument = dao.find(userName);
		currentUserDocument.getFollowing().remove(userName);
		currentUserDocument.setFollowing(currentUserDocument.getFollowing());
		followedUserDocument.setFollowers(followedUserDocument.getFollowers()-1);
		dao.save(currentUserDocument);
		dao.save(followedUserDocument);
		return new ResponseEntity<>(followedUserDocument,HttpStatus.ACCEPTED);
		
	}

	  @GetMapping("/current")
	  public UserDocument getCurrent(@AuthenticationPrincipal final UserDocument user) {
	    return user;
	  }

	  @GetMapping("/connected")
	  public Collection<UserDocument> connectedNotification(@AuthenticationPrincipal final UserDocument currentUser) {
		 return  userService.getAllUsers();
	  }
	
	  @GetMapping("/follow/isfollowing/{user}")
	  public ResponseEntity<Boolean> isFollwoing(@AuthenticationPrincipal final UserDocument currentUser,@PathVariable("user") String userName) {
		  UserDocument currentUserDocument = dao.find(currentUser.getUsername());
		  if(currentUserDocument.getFollowing().contains(userName)) {
			  return new ResponseEntity<>(true,HttpStatus.OK);
		  }
		  return new ResponseEntity<>(false,HttpStatus.OK);
	  }
}
