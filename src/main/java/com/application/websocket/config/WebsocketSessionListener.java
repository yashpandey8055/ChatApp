package com.application.websocket.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.application.bean.OnlineStatus;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.OnlineUserPersistenceService;
import com.application.service.messaging.impl.WebsocketMessagingController;

@Component
public class WebsocketSessionListener{
	
	@Autowired
	@Qualifier("SimpleOnlineUserPersistence")
	OnlineUserPersistenceService onlinePersistence;
	
	@Autowired
	WebsocketMessagingController messageController;

	@EventListener(SessionDisconnectEvent.class)
	public void sessionClose(SessionDisconnectEvent  sde) {
		String user = sde.getUser().getName();
		String connectedUser = onlinePersistence.fetch(user);
		OnlineStatus onlineStatus = new OnlineStatus();
		onlineStatus.setUsername(user);
		onlineStatus.setStatus(RequestResponseConstant.DISCONNECTED);
		if(connectedUser!=null) {
			messageController.fetchUser(connectedUser, onlineStatus);
		}
		onlinePersistence.remove(user);

		
	}
}
