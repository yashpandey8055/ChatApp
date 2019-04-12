package com.application.service.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	

	@GetMapping("/getPosts")
	public ResponseEntity<String> getPost(@AuthenticationPrincipal User currentUser){
		
		return new ResponseEntity<>(null, HttpStatus.OK);
		
		
	}
}
