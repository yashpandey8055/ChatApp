package com.application.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.PostActivityReqResBean;
import com.application.service.DisplayPostService;
import com.application.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	@Qualifier("StatusPost")
	PostService postService;
	
	@Autowired
	@Qualifier("UserSpecific")
	DisplayPostService userPostService;

	@PostMapping("/insert")
	public ResponseEntity<GenericResponseBean> insertPost(@AuthenticationPrincipal User currentUser, @RequestBody PostActivityReqResBean postActivityReqResBean){
		postActivityReqResBean.setUserName(currentUser.getUsername());
		GenericResponseBean post = postService.post(postActivityReqResBean);
		return new ResponseEntity<>(post,HttpStatus.OK);
	}
	
	@GetMapping("/getPost")
	public ResponseEntity<Object> getPost(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		GenericResponseBean post = postService.view(postId,currentUser.getUsername());
		return new ResponseEntity<>(post, HttpStatus.OK);
	}
	
	@GetMapping("/like")
	public ResponseEntity<String> likePost(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		return new ResponseEntity<>("Fail",HttpStatus.OK);
	}
	@GetMapping("/unlike")
	public ResponseEntity<String> unlikePost(@AuthenticationPrincipal User currentUser, @RequestParam String postId){

		return new ResponseEntity<>("fail",HttpStatus.OK);
	}
	
	@GetMapping("/getuserpost/{username}")
	public ResponseEntity<GenericResponseBean> getPosts(@AuthenticationPrincipal User currentUser,@PathVariable("username") String user){			
		return new ResponseEntity<>(userPostService.viewPost(user),HttpStatus.OK);
		
		
	}
}
