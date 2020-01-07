package com.application.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.NotificationBean;
import com.application.bean.User;
import com.application.request.response.constants.RequestResponseConstant;

@RestController
public class RevealController {
	
	@Autowired
	NotificationController notificationController;
	
	@Autowired
	private SimpMessageSendingOperations  messageSendingtemplate ;
	
	
	@GetMapping("/user/reveal")
	public ResponseEntity<String> revealRequest(@AuthenticationPrincipal User currentUser,
			@RequestParam String requestId){
		
		NotificationBean notificationBean = new NotificationBean();
		notificationBean.setNotification(currentUser.getUsername()+" has asked for Revelation.");
		notificationBean.setRead(false);
		notificationBean.setReceiver(requestId);
		notificationBean.setSender(currentUser.getUsername());
		notificationBean.setType("R");
		notificationController.sendNotificationDirectlyToQueue(requestId, notificationBean);
		return new ResponseEntity<>(RequestResponseConstant.SUCCESS_RESPONSE,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/user/reveal/accept")
	public ResponseEntity<String> acceptRequest(@AuthenticationPrincipal User currentUser,
			@RequestParam String requestId){
		messageSendingtemplate.convertAndSendToUser(requestId,"/queue/message","Obla di obla da");
		return new ResponseEntity<>(RequestResponseConstant.SUCCESS_RESPONSE,HttpStatus.OK);
		
	}

}
