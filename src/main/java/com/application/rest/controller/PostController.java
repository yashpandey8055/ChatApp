package com.application.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;

@RestController
@RequestMapping("/posts")
public class PostController {


	@PostMapping("/insert")
	public ResponseEntity<String> insertPost(@AuthenticationPrincipal User currentUser, @RequestBody Object post){

		return new ResponseEntity<>(null,HttpStatus.OK);
	}
	
	@GetMapping("/getPost")
	public ResponseEntity<Object> getPost(@AuthenticationPrincipal User currentUser, @RequestParam String postId){

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@GetMapping("/like")
	public ResponseEntity<String> likePost(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		return new ResponseEntity<>("Fail",HttpStatus.OK);
	}
	@GetMapping("/unlike")
	public ResponseEntity<String> unlikePost(@AuthenticationPrincipal User currentUser, @RequestParam String postId){

		return new ResponseEntity<>("fail",HttpStatus.OK);
	}
}
