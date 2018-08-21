package com.application.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.application.bean.OnlineNotification;
import com.application.controller.GreetingController;
import com.application.service.UserCrudService;

@Component
public class WebSocketSessionListener {

	@Autowired
	GreetingController controller;


	@Autowired
	UserCrudService userService;
	
	private static final String CONNECTED = "CONNECTED";
	private static final String DISCONNECTED = "DISCONNECTED";
	
	@EventListener(SessionConnectedEvent.class)
	public void sessionConnection(SessionConnectedEvent sce) {
		String user = sce.getUser().getName();
		userService.add(user);
		sendNotification(user,CONNECTED);
	}

	@EventListener(SessionDisconnectEvent.class)
	public void sessionDisconnection(SessionDisconnectEvent sde) {
		String user = sde.getUser().getName();
		userService.remove(user);
		sendNotification(user,DISCONNECTED);
	}
	private void sendNotification(String user, String action) {
		OnlineNotification notification = new OnlineNotification();
		notification.setUser(user);
		notification.setAction(action);
		controller.onlineNotification(notification);
	}
	
}
