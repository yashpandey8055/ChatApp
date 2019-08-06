package com.application.service.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;
import com.application.data.dao.FollowCollectionDAOImpl;
import com.application.data.dao.documents.FollowDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.service.UserDetailsService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class CurrentUserController {
	
	@Autowired
	@Qualifier("currentUserService")
	UserDetailsService userDetailService;
	
	@Autowired
	MongoTemplate template;

	@GetMapping("/follow/{user}")
	public ResponseEntity<String> followUser(@AuthenticationPrincipal final User currentUser,@PathVariable("user") String userName){
		
		return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
		
	}

	@GetMapping("/unfollow/{user}")
	public ResponseEntity<String> unfollowUser(@AuthenticationPrincipal final User currentUser,@PathVariable("user") String userName){
		
		return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
		
	}

	  @GetMapping("/current")
	  public ResponseEntity<GenericResponseBean> getCurrent(@AuthenticationPrincipal final User user) {
		  	GenericResponseBean responseBean = userDetailService.getUserDetails(user.getUsername());
			return new ResponseEntity<>(responseBean,responseBean.getCode());
	  }
	  
	  @PostMapping(path = "/changepwd",consumes = "application/json", produces = "application/json")
	  public ResponseEntity<String> getChangePwd(@AuthenticationPrincipal final User user,@RequestBody Object pwdChange) {
			
			return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
	  }


	  @GetMapping("/connected")
	  public ResponseEntity<String> connectedNotification(@AuthenticationPrincipal final User currentUser) {
			
			return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
	  }
	
	  @GetMapping("/follow/isfollowing/{user}")
	  public ResponseEntity<Boolean> isFollwoing(@AuthenticationPrincipal final User currentUser,@PathVariable("user") String userName) {
		  FollowCollectionDAOImpl followDocument = (FollowCollectionDAOImpl) MongoCollectionFactory.getInstance(DataAccessObjectConstants.FOLLOW_DOCUMENT_COLLECTION
					, template);
		
		String[] connection = {currentUser.getUsername(),userName};
		Map<String,Object[]> criteria = new HashMap<>(1);
		criteria.put(DataAccessObjectConstants.CONNECT_PARTICIPANTS, connection);
		
		FollowDocument document = (FollowDocument) followDocument.findWithMutipleKeys(criteria);
			return new ResponseEntity<>(document!=null,HttpStatus.OK);
	  }
	  
	  @GetMapping("/user/{user}")
	  public ResponseEntity<GenericResponseBean> getUser(@AuthenticationPrincipal final User currentUser,@PathVariable("user") String user) {
		  GenericResponseBean responseBean = userDetailService.getUserDetails(user);
			return new ResponseEntity<>(responseBean,responseBean.getCode());
	  }
	  
	  @PutMapping("/user/update")
	  public ResponseEntity<String> updateUser(@AuthenticationPrincipal final User currentUser,@RequestBody User updateUser) {
			
			return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
	  }
	  
}
