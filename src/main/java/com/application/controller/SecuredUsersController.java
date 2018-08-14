package com.application.controller;


import lombok.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.application.bean.UserDocument;
import com.application.config.UUIDAuthenticationService;



@RestController
@RequestMapping("/users")
public class SecuredUsersController {
  @NonNull
  UUIDAuthenticationService authentication;

  @GetMapping("/current")
  public UserDocument getCurrent(@AuthenticationPrincipal final UserDocument user) {
    return user;
  }


}