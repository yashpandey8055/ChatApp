package com.application.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.application.bean.NotificationBean;
import com.application.service.dao.NotificationDao;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.NotificationDocument;
import com.application.service.dao.documents.UserDocument;
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
    	NotificationDocument document = notificationDao.find("postId", postId);
    	
    	NotificationBean notificationBean = new NotificationBean();
    	StringBuilder builder = new StringBuilder(sender);
    	if(document.getLastSender()!=null) {
    		builder.append(","+document.getLastSender());
    	}
    	if(document.getCount()>2) {
    		builder.append(" and "+document.getCount()+" others");
    	}
    	if(action.equalsIgnoreCase(Action.COMMENT_LIKE.name())) {
    		builder.append(" liked you comment.");
    	}
    	if(action.equalsIgnoreCase(Action.COMMENT.name())) {
    		builder.append(" commented on your post");
    	}
    	
    	if(action.equalsIgnoreCase(Action.POST_LIKE.name())) {
    		builder.append(" liked your post");
    	}
    	if(action.equalsIgnoreCase(Action.PHOTO_LIKE.name())) {
    		builder.append(" liked your photo");
    	}
    	if(action.equalsIgnoreCase(Action.VIDEO_LIKE.name())) {
    		builder.append(" liked you video");
    	}
    	List<UserDocument> users =  userDao.findAll(Lists.newArrayList(sender,document.getLastSender()));
    	document.setCount(document.getCount()+1);
    	document.setDate(new Date());
    	document.setLastSender(sender);
    	document.setPostId(postId);
    	document.setRead(false);
    	document.setReceiver(receiver);
    	notificationDao.save(document);
    	notificationBean.setMessage(builder.toString());
    	notificationBean.setTimeAgo("Just Now");
    	notificationBean.setPictureUrls(users.stream().map(UserDocument::getProfileUrl).collect(Collectors.toSet()));	
    	notificationBean.setPostId(postId);
    	sendNotification(receiver, notificationBean);
    }
}
