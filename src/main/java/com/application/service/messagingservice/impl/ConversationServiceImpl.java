package com.application.service.messagingservice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.application.bean.ConversationHandshakeBean;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.ConversationDocument;
import com.application.data.dao.documents.MongoDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.utils.Utils;
import com.google.common.collect.Lists;

@Component
public class ConversationServiceImpl {
	
	@Autowired
	public ConversationServiceImpl(MongoTemplate template) {
		this.template = template;
	}


	MongoTemplate template;
	
	public ConversationHandshakeBean fetch(String sender,String receiver) {
		 ConversationHandshakeBean handshakeBean = new ConversationHandshakeBean();
		 handshakeBean.setUsername(receiver);
		 IMongoCollection conversationCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.CONVERSATION_DOCUMENT_COLLECTION, template);
		 List<? extends MongoDocument> conversatioDocument  = conversationCollection.executeQuery(Query.query(Criteria.where(DataAccessObjectConstants.PARTICIPANTS).all(Lists.newArrayList(sender,receiver))));
		 if(conversatioDocument!=null&&!conversatioDocument.isEmpty()) {
			 handshakeBean.setConversationId(((ConversationDocument)conversatioDocument.get(0)).getConversationId());
		 }
		return handshakeBean;
	}
	
	public ConversationHandshakeBean save(String sender,String receiver) {
		ConversationHandshakeBean handshakeBean = fetch(sender,receiver);
		if(handshakeBean.getConversationId()!=null&&!handshakeBean.getConversationId().isEmpty()) {
			return handshakeBean;
		}
		String conversationId = Utils.randomStringGenerate(10);
		IMongoCollection conversationCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.CONVERSATION_DOCUMENT_COLLECTION, template);
    	ConversationDocument document = new ConversationDocument();
    	document.setConversationId(conversationId);
    	document.setParticipants(Lists.newArrayList(sender,receiver));
    	conversationCollection.save(document);
    	
    	handshakeBean = new ConversationHandshakeBean();
		handshakeBean.setUsername(receiver);
		handshakeBean.setConversationId(conversationId);
    	return handshakeBean;
		
	}
	
	public ConversationDocument fetch(String conversationId) {

		 IMongoCollection conversationCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.CONVERSATION_DOCUMENT_COLLECTION, template);
		 return (ConversationDocument) conversationCollection.findOne(DataAccessObjectConstants.CONVERSATION_ID, conversationId);

	}

}
