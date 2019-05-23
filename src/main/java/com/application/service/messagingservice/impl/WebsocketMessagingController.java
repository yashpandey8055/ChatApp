package com.application.service.messagingservice.impl;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.application.bean.ConversationHandshakeBean;
import com.application.bean.MessageBean;
import com.application.bean.OnlineStatus;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.MessagingService;

@Controller
public class WebsocketMessagingController {

	ConversationServiceImpl conversationImpl;
	
	@Autowired
	public WebsocketMessagingController(SimpMessageSendingOperations messageSendingtemplate,
			MessagingService messageService,ConversationServiceImpl conversationImpl) {
		this.messageSendingtemplate = messageSendingtemplate;
		this.messageService = messageService;
		this.conversationImpl = conversationImpl;
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
    	 GenericResponseBean response = new GenericResponseBean();
    	 ConversationHandshakeBean bean = conversationImpl.fetch(receiver,message.getUsername());
    	 bean.setStatus(message.getStatus());
		 response.setCode(HttpStatus.OK);
		 response.setType(RequestResponseConstant.SUCCESS_RESPONSE);
		 response.setData(bean);
		 messageSendingtemplate.convertAndSendToUser(receiver,"/queue/fetchuser",
				response);
    }
    
   
}
