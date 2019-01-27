package com.application.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.bean.MessageBean;
import com.application.bean.OnlineNotification;
import com.application.bean.PastConversations;
import com.application.service.OnlineUserPersistenceService;
import com.application.service.dao.MessageDao;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.MessageDocument;
import com.application.service.dao.documents.UserDocument;
import com.application.utils.Utils;

@Controller
public class MessageController {

	@Autowired
	private SimpMessageSendingOperations  template ;

	@Autowired
	private MessageDao messagedDao;
	@Autowired
	private OnlineUserPersistenceService onlineUser;
	
	@Autowired
	private UsersDao userDao;
	
    @MessageMapping("/message")
    public void greeting(@Payload MessageBean message, 
    	      Principal principal){
    	MessageDocument document = new MessageDocument();
    	document.setMessage(message.getMessage());
    	document.setReceiver(message.getReceiver());
    	document.setSender(message.getSender());
    	document.setDate(new Date());
    	message.setSenderProfileUrl(Optional.ofNullable(onlineUser.findWithUsername(message.getSender()))
    			.orElse(userDao.find(message.getSender())).getProfileUrl());
    	messagedDao.save(document);
		template.convertAndSendToUser(message.getReceiver(),"/queue/message",message);
    }

    @MessageMapping("/online")
    public void onlineNotification(@Payload OnlineNotification notification) {
    	template.convertAndSend("/queue/online",notification);
    }
    
    @GetMapping("/getMessages")
    public @ResponseBody List<MessageDocument> getMessages(@AuthenticationPrincipal UserDocument user,@RequestParam String receiver,@RequestParam Integer bucket) {
    	 return messagedDao.getMessages(user.getUsername(), receiver,bucket);
    }
    
    @GetMapping("/pastConversations")
    public @ResponseBody Collection<PastConversations> pastConversations(@AuthenticationPrincipal UserDocument user) {
    	List<MessageDocument> messageDocuments =  messagedDao.getPastConversation(user.getUsername());
    	Collections.sort(messageDocuments,Collections.reverseOrder());
    	Map<String, PastConversations> pastConversation = new HashMap<>();
    	for(MessageDocument document: messageDocuments) {
    		String userName = user.getUsername().equals(document.getReceiver())?document.getSender():document.getReceiver();
    		pastConversation.computeIfAbsent(userName, k->insertNewConversation(new PastConversations(), document)).setSender(userName);
    		
    	}
    	List<UserDocument> userDocuments = userDao.findAll(pastConversation.keySet());
    	for(UserDocument userDocument:userDocuments) {
    		pastConversation.get(userDocument.getUsername()).setProfileUrl(userDocument.getProfileUrl());
    	}
    	return pastConversation.values();
    }
    
    private PastConversations insertNewConversation(PastConversations pastConversation,MessageDocument document) {
    	pastConversation.setDaysAgo(Utils.calculateTimeDifference(document.getDate()));
    	pastConversation.setDate(document.getDate());
    	pastConversation.setMessage(document.getMessage());
		return pastConversation;
    	
    }
    
    
}
