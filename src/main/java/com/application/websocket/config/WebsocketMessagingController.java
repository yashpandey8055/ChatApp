package com.application.websocket.config;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.application.bean.MessageBean;
import com.application.bean.User;
import com.application.data.dao.documents.MessageDocument;

@Controller
public class WebsocketMessagingController {
	@Autowired
	private SimpMessageSendingOperations  template ;

    @MessageMapping("/message")
    public void greeting(@Payload MessageBean message, 
    	      Principal principal){
    	MessageDocument document = new MessageDocument();
    	document.setMessage(message.getMessage());
    	document.setReceiver(message.getReceiver());
    	document.setSender(message.getSender());
    	document.setDate(new Date());
		template.convertAndSendToUser(message.getReceiver(),"/queue/message",message);
    }
    
    @GetMapping("/findfriend/register")
    public void registerUser(@AuthenticationPrincipal User user) {
    	
    }
}
