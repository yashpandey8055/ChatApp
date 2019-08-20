package com.application.service.rest.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.NotificationBean;
import com.application.bean.User;
import com.application.service.MessagingService;
import com.application.service.messagingservice.impl.ConversationServiceImpl;
import com.application.service.notificationservice.impl.NotificationServiceImpl;

@Controller
@RestController
public class NotificationController {

	private SimpMessageSendingOperations notificationSendingtemplate;
	
	@Autowired
	@Qualifier("NotificationService")
	private NotificationServiceImpl notificationService;
	
	public CompletableFuture<Boolean> sendAsyncNotification(String receiver,NotificationBean notification) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		return CompletableFuture.supplyAsync(() -> sendNotificationDirectlyToQueue(receiver,notification), executorService);
	}
	
	@Autowired
	public NotificationController(SimpMessageSendingOperations messageSendingtemplate,
			MessagingService messageService,ConversationServiceImpl conversationImpl) {
		this.notificationSendingtemplate = messageSendingtemplate;
	}

	
    @MessageMapping("/notification")
	public boolean sendNotificationDirectlyToQueue(String receiver,NotificationBean notification) {
    	notificationService.save(notification);
    	notificationSendingtemplate.convertAndSendToUser(receiver,"/queue/notification",notification);
    	return true;
	}
    
 
    @GetMapping("/notification/get")
	public  ResponseEntity<List<NotificationBean>> getNotification(@AuthenticationPrincipal User currentUser) {
    	return new ResponseEntity<>(notificationService.get(currentUser.getUsername()),HttpStatus.OK);
	}
    

}
