package com.application.service.messagingservice.impl;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.ConversationHandshakeBean;
import com.application.bean.User;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.ConversationDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.OnlineUserPersistenceService;

@RestController
public class WebsocketOnlineActivityController {
	
	@Autowired
	@Qualifier("SimpleOnlineUserPersistence")
	OnlineUserPersistenceService onlinePersistence;
	
	@Autowired
	MongoTemplate template;
	
	 @GetMapping("/findfriend/register")
	 public void registerUser(@AuthenticationPrincipal User user) {
		 onlinePersistence.save(user.getUsername());
	 }
	 
	 @GetMapping("/findfriend/find")
	 public ResponseEntity<GenericResponseBean> findUser(@AuthenticationPrincipal User user) {
		 String username =  onlinePersistence.fetch(user.getUsername());
		 ConversationHandshakeBean handshakeBean = new ConversationHandshakeBean();
		 handshakeBean.setUsername(username);
		 IMongoCollection conversationCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.CONVERSATION_DOCUMENT_COLLECTION, template);
		 ConversationDocument conversatioDocument =  (ConversationDocument) conversationCollection.executeQuery(Query.query(Criteria.where(DataAccessObjectConstants.PARTICIPANTS).all(Lists.newArrayList(username,user.getUsername()))));
		 handshakeBean.setConversationId(conversatioDocument.getConversationId());
		 GenericResponseBean response = new GenericResponseBean();
		 response.setCode(HttpStatus.OK);
		 response.setType(RequestResponseConstant.SUCCESS_RESPONSE);
		 response.setData(handshakeBean);
		return new ResponseEntity<>(response,HttpStatus.OK);
	 }
}
