package com.application.service.messagingservice.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.bean.User;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.MessageDocument;
import com.application.data.dao.documents.MongoDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.bean.PastConversations;
import com.application.request.response.constants.DataAccessObjectConstants;

@Service("PastMessageService")
public class PastMessagesServiceImpl{
	
	@Autowired
	public PastMessagesServiceImpl(MongoTemplate template) {
		this.template = template;
	}

	private MongoTemplate template;

	public Collection<PastConversations> pastConversations(User user) {
		return null;
	}

}
