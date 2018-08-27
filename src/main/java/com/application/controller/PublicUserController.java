package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.ResponseBean;
import com.application.bean.UserDocument;
import com.application.config.UUIDAuthenticationService;
import com.application.service.UsersDao;
import com.application.utils.HttpUtils;

@RestController
@RequestMapping("/public")
public class PublicUserController {

	@Autowired 
	UUIDAuthenticationService userService;
	
	@Autowired
	UsersDao userDao;
	
	@PostMapping("/users/register")
	public ResponseEntity<Object> register(@RequestBody String request){
		UserDocument document = HttpUtils.unwrap(request, UserDocument.class);
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
}
