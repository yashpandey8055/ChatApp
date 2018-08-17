package com.application.controller;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import org.springframework.stereotype.Controller;

import com.application.bean.MessageBean;
import com.application.bean.OnlineNotification;

@Controller
public class GreetingController {

	@Autowired
	private SimpMessageSendingOperations  template ;
	
	
    @MessageMapping("/message")
    public void greeting(@Payload MessageBean message, 
    	      Principal principal){

		template.convertAndSendToUser(message.getReceiver(),"/queue/message",message);
    }

    @MessageMapping("/online")
    public void onlineNotification(@Payload OnlineNotification notification) {
    	template.convertAndSend("/queue/online",notification);
    }
}
