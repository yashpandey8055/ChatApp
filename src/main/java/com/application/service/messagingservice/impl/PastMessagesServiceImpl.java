package com.application.service.messagingservice.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.application.bean.User;
import com.application.data.dao.documents.MessageDocument;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.model.Filters;

@Service("PastMessageService")
public class PastMessagesServiceImpl{
	
	@Autowired
	public PastMessagesServiceImpl(MongoTemplate template) {
		this.template = template;
	}

	private MongoTemplate template;

	public Collection<MessageDocument> pastConversations(User user) {
		Bson filter = Filters.or(
                Filters.eq(DataAccessObjectConstants.SENDER, user.getUsername()), 
                Filters.eq(DataAccessObjectConstants.RECEIVER,  user.getUsername())
              );
		DistinctIterable<String> list = template.getCollection(DataAccessObjectConstants.MESSAGE_DOCUMENT_COLLECTION)
				.distinct(DataAccessObjectConstants.PARTICIPANTS, filter,String.class);
		Iterator<String> itr = list.iterator();
		List<MessageDocument> messageDocuments = new ArrayList<>();
		while(itr.hasNext()){
			String userName = itr.next();
			Query q = Query.query(Criteria.where(DataAccessObjectConstants.PARTICIPANTS)
					.in(user.getUsername(),userName));
			q.with(new Sort(Sort.Direction.DESC, "date"));
			q.limit(1);
			MessageDocument message = template.findOne(q
					, MessageDocument.class,DataAccessObjectConstants.MESSAGE_DOCUMENT_COLLECTION);
			messageDocuments.add(message);
		}
		return messageDocuments;
	}

}
