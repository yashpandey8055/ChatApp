package com.application.service.onlineuser.impl;


import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.application.bean.OnlineStatus;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.ConversationDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.OnlineUserPersistenceService;
import com.application.service.messagingservice.impl.WebsocketMessagingController;
import com.application.utils.Utils;
import com.google.common.collect.Lists;

@Service("SimpleOnlineUserPersistence")
public class SimpleOnlineUserSaveServiceImpl implements OnlineUserPersistenceService{
	private static final Logger LOG = LoggerFactory.getLogger(SimpleOnlineUserSaveServiceImpl.class);
	@Autowired
	private MongoTemplate template;
	
	@Autowired
	WebsocketMessagingController messageController;
	
	private Set<Relation> relations = new HashSet<>();
	
	@Override
	public synchronized void save(String username) {
		boolean requireNew = true;
		for(Relation relation : relations) {
			LOG.info("Relation going between: {} and {}",relation.getFirstUser(),relation.getSecondUser());
			if(relation.hasSpot()) {
				relation.put(username);
				LOG.info("Relation established between: {} and {}",relation.getFirstUser(),relation.getSecondUser());
				OnlineStatus onlineStatus = new OnlineStatus();
				onlineStatus.setUsername(username);
				onlineStatus.setStatus(RequestResponseConstant.CONNECTED);
				messageController.fetchUser(relation.getOtherUser(username), onlineStatus);
				
				requireNew = false;
			}
		}
		
		if(requireNew) {
			LOG.info("Putting a new Relation for user:: {}",username);
			Relation relation = new Relation();
			relation.setFirstUser(username);
			relations.add(relation);
		}
	}

	@Override
	public synchronized String fetch(String username) {
		String fetchedUser = null;
		for(Relation relation : relations) {
			if(relation.hasUser(username)) {
				IMongoCollection conversationCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.CONVERSATION_DOCUMENT_COLLECTION, template);
		    	ConversationDocument document = new ConversationDocument();
		    	document.setConversationId(Utils.randomStringGenerate(10));
		    	document.setParticipants(Lists.newArrayList(relation.getOtherUser(username),username));
		    	conversationCollection.save(document);
				fetchedUser = relation.getOtherUser(username);
				break;
			}
		}
		return fetchedUser;
	}

	@Override
	public synchronized void remove(String username) {
		for(Relation relation : relations) {
			if(relation.hasUser(username)) {
				relations.remove(relation);
				break;
			}
		}
	}
	
	
	private class Relation{
		
		private String firstUser;
		private String secondUser;
		
		public String getFirstUser() {
			return firstUser;
		}
		public String getOtherUser(String username) {
			if(username.equals(getFirstUser())) {
				return getSecondUser();
			}else if(username.equals(getSecondUser())) {
				return getFirstUser();
			}
			return null;
		}
		public void setFirstUser(String firstUser) {
			this.firstUser = firstUser;
		}
		public String getSecondUser() {
			return secondUser;
		}
		public void setSecondUser(String secondUser) {
			this.secondUser = secondUser;
		}
		
		public void removeUserFromSpot(String username) {
			if(username.equals(getFirstUser())) {
				setFirstUser(null);
			}
			if(username.equals(getSecondUser())) {
				setSecondUser(null);
			}
			
		}
		public boolean hasSpot() {
			return getFirstUser()==null||getSecondUser()==null;
		}
		
		public boolean hasUser(String username) {
			return username.equals(getFirstUser())||username.equals(getSecondUser());
		}
		public Relation put(String username) {
			if(getFirstUser()==null) {
				setFirstUser(username);
			}
			if(getSecondUser()==null) {
				setSecondUser(username);
			}
			return this;
		}
	}

}
