package com.application.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;
import com.application.service.messagingservice.impl.PastMessagesServiceImpl;

@RestController
@RequestMapping("/message")
public class SimpleMessagingController {

	@Autowired
	MongoTemplate template;
	
	@Autowired
	PastMessagesServiceImpl pastMessages;
	
	@GetMapping("/pastConversations")
	public ResponseEntity<Object> getPost(@AuthenticationPrincipal User currentUser){
		return new ResponseEntity<>(pastMessages.pastConversations(currentUser),HttpStatus.OK);
	}
}
