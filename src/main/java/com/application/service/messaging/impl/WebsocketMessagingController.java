package com.application.service.messaging.impl;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import com.application.bean.MessageBean;
import com.application.bean.OnlineStatus;
import com.application.data.dao.documents.MessageDocument;

@Controller
public class WebsocketMessagingController {
	@Autowired
	private SimpMessageSendingOperations  template ;

    @MessageMapping("/message")
    public void sendMessage(@Payload MessageBean message, 
    	      Principal principal){
    	MessageDocument document = new MessageDocument();
    	document.setMessage(message.getMessage());
    	document.setReceiver(message.getReceiver());
    	document.setSender(message.getSender());
    	document.setDate(new Date());
		template.convertAndSendToUser(message.getReceiver(),"/queue/message",message);
    }
    
    @MessageMapping("/fetchuser")
    public void fetchUser(String receiver, 
    	      OnlineStatus message){
    	template.convertAndSendToUser(receiver,"/queue/fetchuser",message);
    }
    
   
}
