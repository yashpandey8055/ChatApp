package com.application.controller;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import org.springframework.stereotype.Controller;

import com.application.bean.MessageBean;
import com.application.bean.OnlineNotification;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class GreetingController {

	@Autowired
	private SimpMessageSendingOperations  template ;
	
	
    @MessageMapping("/message")
    public void greeting(@Payload MessageBean message, 
    	      Principal principal){
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.readValue(src, valueTypeRef)
		template.convertAndSendToUser(message.getReceiver(),"/queue/message",message);
    }

    @MessageMapping("/online")
    public void onlineNotification(@Payload OnlineNotification online,Principal principal) {
    	template.convertAndSend("/queue/online",online);
    }
}
