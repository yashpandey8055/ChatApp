package com.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.application.bean.OnlineNotification;
import com.application.bean.UserDocument;
import com.application.controller.GreetingController;
import com.application.service.UserCrudService;
import com.application.service.UsersDao;

@Component
public class WebSocketSessionListener {

	@Autowired
	GreetingController controller;

	@Autowired
	UsersDao userDao;
	
	@Autowired
	UserCrudService userService;
	
	private static final String CONNECTED = "CONNECTED";
	private static final String DISCONNECTED = "DISCONNECTED";
	
	@EventListener(SessionConnectedEvent.class)
	public void sessionConnection(SessionConnectedEvent sce) {
		String user = sce.getUser().getName();
		UserDocument userDocument = userDao.find(user);
		userService.add(userDocument.getId());
		sendNotification(userDocument,CONNECTED);
	}

	@EventListener(SessionDisconnectEvent.class)
	public void sessionDisconnection(SessionDisconnectEvent sde) {
		String user = sde.getUser().getName();
		UserDocument userDocument = userDao.find(user);
		userService.remove(userDocument.getId());
		UserDocument document = new UserDocument();
		document.setId(userDocument.getId());
		sendNotification(document,DISCONNECTED);
	}
	private void sendNotification(UserDocument user, String action) {
		OnlineNotification notification = new OnlineNotification();
		notification.setUser(user);
		notification.setAction(action);
		controller.onlineNotification(notification);
	}
	
}
