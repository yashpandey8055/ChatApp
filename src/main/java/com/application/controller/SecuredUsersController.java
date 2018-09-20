package com.application.controller;


import lombok.NonNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.config.UUIDAuthenticationService;
import com.application.service.UserCrudService;
import com.application.service.dao.documents.UserDocument;



@RestController
@RequestMapping("/users")
public class SecuredUsersController {
  @NonNull
  UUIDAuthenticationService authentication;

	@Autowired
	UserCrudService userService;
  @GetMapping("/current")
  public UserDocument getCurrent(@AuthenticationPrincipal final UserDocument user) {
    return user;
  }

  @GetMapping("/connected")
  public List<UserDocument> connectedNotification() {
  	return userService.getAllUsers();
  }

}