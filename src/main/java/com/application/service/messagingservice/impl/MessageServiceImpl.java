package com.application.service.messagingservice.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.application.bean.MessageBean;
import com.application.data.dao.IMongoCollection;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.service.MessagingService;

@Service("MessageService")
public class MessageServiceImpl implements MessagingService{
	
	private MongoTemplate template;

	public MessageServiceImpl(MongoTemplate template) {
		super();
		this.template = template;
	}

	@Override
	public void save(MessageBean messageBean) {
		IMongoCollection postCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.MESSAGE_DOCUMENT_COLLECTION
				, template);

		
	}

	@Override
	public List<MessageBean> view(MessageBean messageBean) {
		// TODO Auto-generated method stub
		return null;
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
