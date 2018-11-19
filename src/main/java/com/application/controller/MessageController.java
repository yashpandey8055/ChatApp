package com.application.controller;

import java.security.Principal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.bean.MessageBean;
import com.application.bean.OnlineNotification;
import com.application.bean.PastConversations;
import com.application.service.dao.MessageDao;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.MessageDocument;
import com.application.service.dao.documents.UserDocument;

@Controller
public class MessageController {

	@Autowired
	private SimpMessageSendingOperations  template ;

	@Autowired
	private MessageDao messagedDao;
	
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
    	message.setSenderProfileUrl(userDao.findProfileUrl(message.getSender()).getProfileUrl());
    	messagedDao.save(document);
		template.convertAndSendToUser(message.getReceiver(),"/queue/message",message);
    }

    @MessageMapping("/online")
    public void onlineNotification(@Payload OnlineNotification notification) {
    	template.convertAndSend("/queue/online",notification);
    }
    
    @GetMapping("/getMessages")
    public @ResponseBody List<MessageDocument> getMessages(Principal user,@RequestParam String receiver) {
    	return messagedDao.getMessages(user.getName(), receiver);
    }
    
    @GetMapping("/pastConversations")
    public @ResponseBody Collection<PastConversations> pastConversations(Principal user) {
    	List<MessageDocument> messageDocuments =  messagedDao.getPastConversation(user.getName());
    	Collections.sort(messageDocuments);
    	Map<String, PastConversations> pastConversation = new HashMap<>();
    	for(MessageDocument document: messageDocuments) {
    		String userName = document.getReceiver().equals(user.getName())?document.getSender():document.getReceiver();
    		pastConversation.computeIfAbsent(userName, k->insertNewConversation(new PastConversations(), document,userName)).setSender(userName);
    		
    	}
    	List<UserDocument> userDocuments = userDao.findAll(pastConversation.keySet());
    	for(UserDocument userDocument:userDocuments) {
    		pastConversation.get(userDocument.getUsername()).setProfileUrl(userDocument.getProfileUrl());
    	}
    	return pastConversation.values();
    	
    }
    
    private PastConversations insertNewConversation(PastConversations pastConversation,MessageDocument document, String userName) {
    	pastConversation.setDaysAgo(calculateTimeDifference(document.getDate()));
    	pastConversation.setDate(document.getDate());
    	pastConversation.setMessage(document.getMessage());
		return pastConversation;
    	
    }
    
    static String calculateTimeDifference(Date date) {
    	Calendar cal = Calendar.getInstance();
    	long[] timeCount = {60,60,24,30,365};
    	String[] time = {"s","m","d","mon","y"};
    	float seconds = (cal.getTimeInMillis() - date.getTime())/1000;
    	int count=0;
    	while(true) {
    		if((seconds=seconds/timeCount[count])<=1) {
    			break;
    		}else {
    			count++;
    		}
    	}
    	return ((int)(seconds*timeCount[count]))+" "+time[count]+" ago";
    }
}
