package com.application.service.messagingservice.impl;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import com.application.bean.MessageBean;
import com.application.bean.OnlineStatus;
import com.application.service.MessagingService;

@Controller
public class WebsocketMessagingController {
	
	@Autowired
	public WebsocketMessagingController(SimpMessageSendingOperations messageSendingtemplate,
			MessagingService messageService) {
		this.messageSendingtemplate = messageSendingtemplate;
		this.messageService = messageService;
	}

	private SimpMessageSendingOperations  messageSendingtemplate ;
	
	private MessagingService messageService;

    @MessageMapping("/message")
    public void sendMessage(@Payload MessageBean message, 
    	      Principal principal){
    	messageService.save(message);
    	messageSendingtemplate.convertAndSendToUser(message.getReceiver(),"/queue/message",message);
    }
    
    @MessageMapping("/fetchuser")
    public void fetchUser(String receiver, 
    	      OnlineStatus message){
    	messageSendingtemplate.convertAndSendToUser(receiver,"/queue/fetchuser",message);
    }
    
   
}
