package com.application.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;
import com.application.request.response.constants.GeneralConstants;
import com.application.service.DoUndoActionExecuteService;
import com.application.service.IDoUndoAction;
import com.application.service.action.doundoactionservice.impl.DoActionImpl;

@RestController
public class LikeController {
	
	@Autowired
	@Qualifier("PostLikeAction")
	IDoUndoAction likeUnlikeService;
	
	@GetMapping("/like/post")
	public ResponseEntity<String> likePost(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		DoUndoActionExecuteService likeService = new DoActionImpl(likeUnlikeService);
		likeService.execute(postId,GeneralConstants.POST_TYPE,currentUser.getUsername());
		return new ResponseEntity<>(GeneralConstants.LIKED_MSG,HttpStatus.OK);
	}
	@GetMapping("/unlike/post")
	public ResponseEntity<String> unlikePost(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		DoUndoActionExecuteService unlikeService = new DoActionImpl(likeUnlikeService);
		unlikeService.execute(postId,GeneralConstants.POST_TYPE,currentUser.getUsername());
		return new ResponseEntity<>(GeneralConstants.LIKED_MSG,HttpStatus.OK);
	}
	
	@GetMapping("/like/comment")
	public ResponseEntity<String> likeComment(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		DoUndoActionExecuteService likeService = new DoActionImpl(likeUnlikeService);
		likeService.execute(postId,GeneralConstants.COMMENT_TYPE,currentUser.getUsername());
		return new ResponseEntity<>(GeneralConstants.LIKED_MSG,HttpStatus.OK);
	}
	@GetMapping("/unlike/comment")
	public ResponseEntity<String> unlikeComment(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		DoUndoActionExecuteService unlikeService = new DoActionImpl(likeUnlikeService);
		unlikeService.execute(postId,GeneralConstants.COMMENT_TYPE,currentUser.getUsername());
		return new ResponseEntity<>(GeneralConstants.LIKED_MSG,HttpStatus.OK);
	}
}
