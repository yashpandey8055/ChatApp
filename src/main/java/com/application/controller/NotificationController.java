package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import com.application.bean.MessageBean;
import com.application.service.dao.NotificationDao;
import com.application.service.dao.documents.NotificationDocument;
import com.application.service.dao.documents.UserDocument;

@Controller
public class NotificationController {
	@Autowired
	private SimpMessageSendingOperations  template ;
	
	@Autowired
	private NotificationDao notificationDao;
	
    @MessageMapping("/notification")
    public void sendNotification(@Payload MessageBean message,String postId,
    	      @AuthenticationPrincipal UserDocument principal){
    	NotificationDocument document;
    	document = notificationDao.find("postId", postId);
    	if(document!=null) {
    		document.setNotification(message.getSender()+" and "+document.getCount()+" other morons liked your post.");
    		document.setCount(document.getCount()+1);
    		
    	}else {
	    	document = new NotificationDocument();
	    	document.setNotification(message.getMessage());
	    	document.setPostId(postId);
	    	document.setCount(0);
	    	document.setReceiver(message.getReceiver());
    	}
    	document.setRead(false);
    	message.setMessage(document.getNotification());
    	notificationDao.save(document);
		template.convertAndSendToUser(message.getReceiver(),"/queue/notification",message);
    }

}
