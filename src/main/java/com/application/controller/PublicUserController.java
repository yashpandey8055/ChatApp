package com.application.controller;

import java.util.Calendar;
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

import com.application.config.UUIDAuthenticationService;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.UserDocument;
import com.application.utils.HttpUtils;

@RestController
@RequestMapping("/public")
public class PublicUserController {

	@Autowired 
	UUIDAuthenticationService userService;
	
	@Autowired
	UsersDao userDao;
	
	@PostMapping("/users/register")
	public ResponseEntity<Object> register(@RequestBody UserDocument document){
		document.setAge(Calendar.getInstance().get(Calendar.YEAR)-document.getYearOfBirth());
		document.setProfileUrl("https://s3.ap-south-1.amazonaws.com/ketu-user-profile-pictures/default.jpg");
		userDao.save(document);
		return login(document.getUsername());
	}
	
	@GetMapping("/users/login")
	public ResponseEntity<Object> login(@RequestParam String userName){
		if(userDao.find(userName) == null) {
			return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userService.login(userName),HttpStatus.OK);
	}

	@GetMapping("/users/getUser")
	public ResponseEntity<UserDocument> getUser(@RequestParam String token){
		
		return new ResponseEntity<>(userService.findByToken(token),HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/users/pushUsers")
	public ResponseEntity<String> pushUsers(@RequestBody List<UserDocument> documents){
		userDao.pushList(documents);
		return new ResponseEntity<>("Hello",HttpStatus.ACCEPTED);
	}
}
