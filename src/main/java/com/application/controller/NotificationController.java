package com.application.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.application.bean.NotificationBean;
import com.application.service.dao.NotificationDao;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.NotificationDocument;
import com.application.service.dao.documents.UserDocument;
import com.application.utils.Utils;
import com.google.common.collect.Lists;

@Controller
public class NotificationController {
	
	public enum Action{
		COMMENT_LIKE,COMMENT, POST_LIKE,PHOTO_LIKE,VIDEO_LIKE
	}
	
	@Autowired
	NotificationDao notificationDao;
	
	@Autowired
	UsersDao userDao;
	
	@Autowired
	private SimpMessageSendingOperations  template ;
	
    @MessageMapping("/notification")
	public void sendNotification(String receiver,NotificationBean notification) {
		template.convertAndSendToUser(receiver,"/queue/notification",notification);
	}
    
    public void sendNotification(String receiver,String action,String postId,String sender) {
		NotificationDocument document = notificationDao.findOneByQuery(Query.query( Criteria
				.where("type").is(action).and("postId").is(postId)));
    	
    	NotificationBean notificationBean = new NotificationBean();
    	StringBuilder builder = new StringBuilder(sender);

    	if(action.equalsIgnoreCase(Action.COMMENT_LIKE.name())) {	
    		builder.append(" liked you comment.");
    	}
    	if(action.equalsIgnoreCase(Action.COMMENT.name())) {
    		builder.append(" commented on your post");
    	}
    	
    	if(action.equalsIgnoreCase(Action.POST_LIKE.name())) {
    		builder.append(" liked your post");
    	}
    	document.setCount(document.getCount()+1);
    	if(document.getLastSender()!=null&&document.getLastSender()!=sender) {
    		builder.append(","+document.getLastSender());
    	}
    	if(document.getCount()>2) {
    		builder.append(" and "+(document.getCount()-2)+" others");
    	}
    	Set<String> pictureUrls =  userDao.findAll(Lists.newArrayList(sender,document.getLastSender())).
    			stream().map(UserDocument::getProfileUrl).collect(Collectors.toSet());
    	document.setDate(new Date());
    	document.setLastSender(document.getCurrentSender());
    	document.setPostId(postId);
    	document.setRead(false);
    	document.setReceiver(receiver);
    	document.setMessage(builder.toString());
    	document.setCurrentSender(sender);
    	document.setPictureUrl(pictureUrls);
    	document.setType(action);
    	notificationDao.save(document);
    	notificationBean.setMessage(builder.toString());
    	notificationBean.setTimeAgo("Just Now");
    	notificationBean.setPictureUrls(pictureUrls);	
    	notificationBean.setPostId(postId);
    	notificationBean.setType(action);
    	sendNotification(receiver, notificationBean);
    }
    
    @GetMapping("/get/notification")
	public  ResponseEntity<List<NotificationBean>> getNotification(@AuthenticationPrincipal UserDocument currentUser) {
    	List<NotificationDocument> document = notificationDao.findAll("receiver", currentUser.getUsername());
    	List<NotificationBean> notificationBeanList = new ArrayList<>();
    	for(NotificationDocument notification:document) {
    		NotificationBean notificationBean = new NotificationBean();
    		notificationBean.setMessage(notification.getMessage());
        	notificationBean.setTimeAgo(Utils.calculateTimeDifference(notification.getDate()));
        	notificationBean.setPictureUrls(notification.getPictureUrl());	
        	notificationBean.setPostId(notification.getPostId());
        	notificationBean.setType(notification.getType());
        	notificationBeanList.add(notificationBean);
    	}
		return new ResponseEntity<>(notificationBeanList,HttpStatus.OK);
	}
    

}
