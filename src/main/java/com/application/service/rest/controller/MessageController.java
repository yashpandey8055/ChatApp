package com.application.service.rest.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.bean.User;


@Controller
public class MessageController {

	
    @MessageMapping("/message")
    public void greeting(@Payload Object message, 
    	      Principal principal){
    	
    }

    @MessageMapping("/online")
    public void onlineNotification(@Payload Object notification) {
    
    }
    
    @GetMapping("/getMessages")
    public @ResponseBody List<String> getMessages(@AuthenticationPrincipal User user,@RequestParam String receiver,@RequestParam Integer bucket) {
    	 return null;
    }
    
    @GetMapping("/pastConversations")
    public @ResponseBody Collection<String> pastConversations(@AuthenticationPrincipal Object user) {
    	return null;
    }

    
}
