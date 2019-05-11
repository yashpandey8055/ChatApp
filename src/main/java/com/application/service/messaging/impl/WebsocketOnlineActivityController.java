package com.application.service.messaging.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.OnlineUserPersistenceService;

@RestController
public class WebsocketOnlineActivityController {
	
	@Autowired
	@Qualifier("SimpleOnlineUserPersistence")
	OnlineUserPersistenceService onlinePersistence;
	
	 @GetMapping("/findfriend/register")
	 public void registerUser(@AuthenticationPrincipal User user) {
		 onlinePersistence.save(user.getUsername());
	 }
	 
	 @GetMapping("/findfriend/find")
	 public ResponseEntity<GenericResponseBean> findUser(@AuthenticationPrincipal User user) {
		 String username =  onlinePersistence.fetch(user.getUsername());
		 GenericResponseBean response = new GenericResponseBean();
		 response.setCode(HttpStatus.OK);
		 response.setType(RequestResponseConstant.SUCCESS_RESPONSE);
		 response.setData(username);
		return new ResponseEntity<>(response,HttpStatus.OK);
	 }
}
