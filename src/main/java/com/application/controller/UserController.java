package com.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.UserDocument;

@RestController
@RequestMapping("/public")
public class UserController {
	
	@Autowired
	UsersDao dao;
	@GetMapping("/users/getUser")
	public ResponseEntity<Object> register(@RequestParam String userName){
		
		return new ResponseEntity<>("Hello",HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/users/pushUsers")
	public ResponseEntity<String> pushUsers(@RequestBody List<UserDocument> documents){
		dao.pushList(documents);
		return new ResponseEntity<>("Hello",HttpStatus.ACCEPTED);
	}
	
}
