package com.application.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;
import com.application.request.response.constants.GeneralConstants;
import com.application.service.DoUndoActionExecuteService;
import com.application.service.IDoUndoAction;
import com.application.service.action.doundoactionservice.impl.DoActionImpl;

@RestController
@RequestMapping("/user")
public class FollowController {

	@Autowired
	@Qualifier("UserFollowAction")
	IDoUndoAction userFollowUnfollowAction;
	
	@Autowired
	@Qualifier("ConnectionAcceptRequest")
	IDoUndoAction acceptRejectConnection;
	
	@GetMapping("/connect/{user}")
	public ResponseEntity<String> followUser(@AuthenticationPrincipal User currentUser, @PathVariable("user") String userName){
		DoUndoActionExecuteService followUser = new DoActionImpl(userFollowUnfollowAction);
		followUser.execute(userName,GeneralConstants.CONNECT_USER_TYPE,currentUser.getUsername());
		return new ResponseEntity<>(GeneralConstants.CONNECTED_MSG,HttpStatus.OK);
	}
	
	@GetMapping("/disconnect/{user}")
	public ResponseEntity<String> unfollowUser(@AuthenticationPrincipal User currentUser,@PathVariable("user") String userName){
		DoUndoActionExecuteService unfollowUser = new DoActionImpl(userFollowUnfollowAction);
		unfollowUser.execute(userName,GeneralConstants.DISCONNECT_USER_TYPE,currentUser.getUsername());
		return new ResponseEntity<>(GeneralConstants.DISCONNECTED_MSG,HttpStatus.OK);
	}
	
	@GetMapping("/connect/accept/{user}")
	public ResponseEntity<String> acceptRequest(@AuthenticationPrincipal User currentUser,@PathVariable("user") String userName){
		DoUndoActionExecuteService unfollowUser = new DoActionImpl(acceptRejectConnection);
		unfollowUser.execute(userName,GeneralConstants.ACCEPT_CONNECT_TYPE,currentUser.getUsername());
		return new ResponseEntity<>(GeneralConstants.CONNECTED_MSG,HttpStatus.OK);
	}
	
	@GetMapping("/disconnect/reject/{user}")
	public ResponseEntity<String> rejectRequest(@AuthenticationPrincipal User currentUser,@PathVariable("user") String userName){
		DoUndoActionExecuteService unfollowUser = new DoActionImpl(userFollowUnfollowAction);
		unfollowUser.execute(userName,GeneralConstants.REJECT_CONNECT_TYPE,currentUser.getUsername());
		return new ResponseEntity<>(GeneralConstants.DISCONNECTED_MSG,HttpStatus.OK);
	}
}
