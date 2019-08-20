package com.application.service.notificationservice.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.application.bean.NotificationBean;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.MongoDocument;
import com.application.data.dao.documents.NotificationDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.utils.DateUtils;

@Service("NotificationService")
public class NotificationServiceImpl{
	private MongoTemplate template;

	public NotificationServiceImpl(MongoTemplate template) {
		super();
		this.template = template;
	}
	
	public void save(NotificationBean notification) {
		IMongoCollection notificationCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.NOTIFICATION_DOCUMENT_COLLECTION
				, template);
		NotificationDocument document = new NotificationDocument();
		document.setDate(new Date());
		document.setMessage(notification.getNotification());
		document.setReceiver(notification.getReceiver());
		document.setCurrentSender(notification.getSender());
		document.setRedirectUrl(notification.getRedirectUrl());
		document.setPictureUrl(notification.getSenderProfileUrl());
		document.setRead(false);
		notificationCollection.save(document);
	}

	public List<NotificationBean> get(String userName) {
		IMongoCollection notificationCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.NOTIFICATION_DOCUMENT_COLLECTION
				, template);
		List<? extends MongoDocument> documents = notificationCollection.executeQuery
				(Query.query(Criteria.where(DataAccessObjectConstants.RECEIVER).is(userName)).limit(5));
		List<NotificationBean> notifications = new ArrayList<>(5);
		for(MongoDocument document : documents) {
			NotificationDocument doc = (NotificationDocument) document;
			NotificationBean bean =new  NotificationBean();
			bean.setTimeAgo(DateUtils.calculateTimeDifference(doc.getDate()));
			bean.setNotification(doc.getMessage());
			bean.setReceiver(doc.getReceiver());
			bean.setSender(doc.getCurrentSender());
			bean.setRedirectUrl(doc.getRedirectUrl());
			bean.setSenderProfileUrl(doc.getPictureUrl());
			bean.setRead(doc.getRead());
			notifications.add(bean);
		}
		
		return notifications;
	}
}
