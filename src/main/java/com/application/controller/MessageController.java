package com.application.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;


import com.application.bean.MessageBean;
import com.application.bean.OnlineNotification;
import com.application.service.dao.MessageDao;
import com.application.service.dao.documents.MessageDocument;

@Controller
public class MessageController {

	@Autowired
	private SimpMessageSendingOperations  template ;

	@Autowired
	private MessageDao dao;
	
    @MessageMapping("/message")
    public void greeting(@Payload MessageBean message, 
    	      Principal principal){
    	MessageDocument document = new MessageDocument();
    	document.setMessage(message.getMessage());
    	document.setReceiver(message.getReceiver());
    	document.setSender(message.getSender());
    	dao.save(document);
		template.convertAndSendToUser(message.getReceiver(),"/queue/message",message);
    }

    @MessageMapping("/online")
    public void onlineNotification(@Payload OnlineNotification notification) {
    	template.convertAndSend("/queue/online",notification);
    }
    
    
}
