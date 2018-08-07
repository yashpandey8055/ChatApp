package com.application.controller;



import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.api.UserAuthenticationService;
import com.application.api.UserCrudService;
import com.application.bean.UserDocument;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;



@RestController
@RequestMapping("/public/users")
public class PublicUsersController {
	  @Autowired
	  private MongoTemplate template;
	  
	  @Autowired
	  UserAuthenticationService authentication;
	  
	  @Autowired
	  UserCrudService users;

	  @PostMapping("/register")
	  String register(@RequestBody UserDocument user) {
		  user.setId(UUID.randomUUID().toString());
	    template.save(user);
	    return login(user.getUserName(),user.getPassword());
	  }

	  @PostMapping("/login")
	  String login(@RequestParam("username") final String username,
	    @RequestParam("password") final String password) {
	    return authentication
	      .login(username, password)
	      .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
	  }

}
