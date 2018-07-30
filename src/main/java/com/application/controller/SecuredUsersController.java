package com.application.controller;


import lombok.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.api.UserAuthenticationService;
import com.application.bean.User;



@RestController
@RequestMapping("/users")
public class SecuredUsersController {
  @NonNull
  UserAuthenticationService authentication;

  @GetMapping("/current")
  public User getCurrent(@AuthenticationPrincipal final User user) {
    return user;
  }

  @GetMapping("/some")
  public String gteSome(String user) {
    return "HeloWorld";
  }
  @GetMapping("/logout")
  public boolean logout(@AuthenticationPrincipal final User user) {
    authentication.logout(user);
    return true;
  }
}