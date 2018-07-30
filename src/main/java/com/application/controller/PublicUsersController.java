package com.application.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.api.UserAuthenticationService;
import com.application.api.UserCrudService;
import com.application.bean.User;



@RestController
@RequestMapping("/public/users")
public class PublicUsersController {
	  @Autowired
	  UserAuthenticationService authentication;
	  @Autowired
	  UserCrudService users;

	  @PostMapping("/register")
	  String register(
	    @RequestParam("username") final String username,
	    @RequestParam("password") final String password) {
	    users
	      .save(
	        User
	          .builder()
	          .id(username)
	          .username(username)
	          .password(password)
	          .build()
	      );

	    return login(username, password);
	  }

	  @PostMapping("/login")
	  String login(
	    @RequestParam("username") final String username,
	    @RequestParam("password") final String password) {
	    return authentication
	      .login(username, password)
	      .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
	  }
}
