package com.application.service.messagingservice.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.application.bean.MessageBean;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.MessageDocument;
import com.application.data.dao.documents.MongoDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.service.MessagingService;
import com.google.common.collect.Lists;

@Service("MessageService")
public class MessageServiceImpl implements MessagingService{
	
	private MongoTemplate template;

	public MessageServiceImpl(MongoTemplate template) {
		super();
		this.template = template;
	}

	@Override
	public void save(MessageBean messageBean) {
		IMongoCollection messageCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.MESSAGE_DOCUMENT_COLLECTION
				, template);
		List<String> participants = Lists.newArrayList(messageBean.getReceiver(),messageBean.getSender());
		MessageDocument document = new MessageDocument();
		document.setDate(new Date());
		document.setMessage(messageBean.getMessage());
		document.setReceiver(messageBean.getReceiver());
		document.setSender(messageBean.getSender());
		document.setParticipants(participants);
		messageCollection.save(document);

	}

	@Override
	public List<MessageBean> view(MessageBean messageBean) {
		IMongoCollection messageCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.MESSAGE_DOCUMENT_COLLECTION
				, template);
		List<String> participants = Lists.newArrayList(messageBean.getReceiver(),messageBean.getSender());
		Query query = Query.query(Criteria.where(DataAccessObjectConstants.PARTICIPANTS)
				.all(participants));
		List<? extends MongoDocument> documents = messageCollection.executeQuery(query);
		List<MessageBean> messages = new ArrayList<>();
		for(MongoDocument document:documents) {
			MessageDocument messageDocument = (MessageDocument)document;
			MessageBean message = new MessageBean(messageBean);
			message.setMessage(messageDocument.getMessage());
			messages.add(message);
		}
		return messages;
	}

	@Override
	public boolean update(MessageBean messageBean) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(MessageBean messageBean) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
