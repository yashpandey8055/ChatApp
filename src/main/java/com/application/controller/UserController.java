package com.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@GetMapping("/users/getUser")
	public ResponseEntity<Object> register(@RequestParam String userName){
		
		return new ResponseEntity<>("Hello",HttpStatus.ACCEPTED);
	}
}
