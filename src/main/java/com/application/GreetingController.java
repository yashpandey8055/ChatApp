package com.application;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

	@Autowired
	private SimpMessageSendingOperations  template ;
	
	
    @MessageMapping("/message")
    public void greeting(@Payload String message, 
    	      Principal principal){

		template.convertAndSendToUser("satish","/queue/queue1",message);
    }

}
