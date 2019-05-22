package com.application.service.messagingservice.impl;

import java.security.Principal;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.application.bean.ConversationHandshakeBean;
import com.application.bean.MessageBean;
import com.application.bean.OnlineStatus;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.ConversationDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.service.MessagingService;

@Controller
public class WebsocketMessagingController {


	@Autowired
	public WebsocketMessagingController(SimpMessageSendingOperations messageSendingtemplate,
			MessagingService messageService,MongoTemplate template) {
		this.messageSendingtemplate = messageSendingtemplate;
		this.messageService = messageService;
		this.template = template;
	}

	private SimpMessageSendingOperations  messageSendingtemplate ;
	
	private MessagingService messageService;

	private MongoTemplate template;

    @MessageMapping("/message")
    public void sendMessage(@Payload MessageBean message, 
    	      Principal principal){
    	messageService.save(message);
    	messageSendingtemplate.convertAndSendToUser(message.getReceiver(),"/queue/message",message);
    }
    
    @MessageMapping("/fetchuser")
    public void fetchUser(String receiver, 
    	      OnlineStatus message){
		
    	ConversationHandshakeBean handshakeBean = new ConversationHandshakeBean();
		handshakeBean.setUsername(message.getUsername());
		IMongoCollection conversationCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.CONVERSATION_DOCUMENT_COLLECTION, template);
		ConversationDocument conversatioDocument =  (ConversationDocument) conversationCollection.executeQuery
				 (Query.query(Criteria.where(DataAccessObjectConstants.PARTICIPANTS).all(Lists.newArrayList(receiver,message.getUsername()))));
		handshakeBean.setConversationId(conversatioDocument.getConversationId());
    	
		messageSendingtemplate.convertAndSendToUser(receiver,"/queue/fetchuser",handshakeBean);
    }
    
   
}
