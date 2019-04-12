package com.application.service.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.application.bean.User;

@Controller
public class NotificationController {

	
    @MessageMapping("/notification")
	public void sendNotification(String receiver,Object notification) {
	}
    
    public void sendNotification(String receiver,String action,String postId,String sender) {
    	
    }
    
    @GetMapping("/get/notification")
	public  ResponseEntity<String> getNotification(@AuthenticationPrincipal User currentUser) {
    	
		return new ResponseEntity<>(null,HttpStatus.OK);
	}
    

}
