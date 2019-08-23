package com.application.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.NotificationBean;
import com.application.bean.User;
import com.application.data.dao.PostCollectionDAOImpl;
import com.application.data.dao.documents.PostDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.GeneralConstants;
import com.application.service.DoUndoActionExecuteService;
import com.application.service.IDoUndoAction;
import com.application.service.action.doundoactionservice.impl.DoActionImpl;
import com.application.service.action.doundoactionservice.impl.UndoActionImpl;

@RestController
public class LikeController {
	
	@Autowired
	@Qualifier("PostLikeAction")
	IDoUndoAction likeUnlikeService;
	
	
	@Autowired
	private MongoTemplate template;
	
	@Autowired
	NotificationController notificationService;
	
	@GetMapping("/like/post")
	public ResponseEntity<String> likePost(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		DoUndoActionExecuteService likeService = new DoActionImpl(likeUnlikeService);
		likeService.execute(postId,GeneralConstants.POST_TYPE,currentUser.getUsername());
		
		PostCollectionDAOImpl postCollection = (PostCollectionDAOImpl) MongoCollectionFactory.getInstance(DataAccessObjectConstants.POST_DOCUMENT_COLLECTION
				, template);
		PostDocument post = (PostDocument)postCollection.findOne(DataAccessObjectConstants.ID_FIELD, postId);
		NotificationBean notification = new NotificationBean();
		notification.setNotification(currentUser.getUsername()+" Liked your post. Thank Them.");
		notification.setRedirectUrl("/post?postId="+postId);
		notification.setReceiver(post.getUsername());
		notification.setSender(currentUser.getUsername());
		notification.setSenderProfileUrl(currentUser.getProfileUrl());
		notification.setTimeAgo("just now");
		notificationService.sendNotificationDirectlyToQueue(post.getUsername(), notification);
		return new ResponseEntity<>(GeneralConstants.LIKED_MSG,HttpStatus.OK);
	}
	@GetMapping("/unlike/post")
	public ResponseEntity<String> unlikePost(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		DoUndoActionExecuteService unlikeService = new UndoActionImpl(likeUnlikeService);
		unlikeService.execute(postId,GeneralConstants.POST_TYPE,currentUser.getUsername());
		return new ResponseEntity<>(GeneralConstants.LIKED_MSG,HttpStatus.OK);
	}
	
	@GetMapping("/like/comment")
	public ResponseEntity<String> likeComment(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		DoUndoActionExecuteService likeService = new DoActionImpl(likeUnlikeService);
		likeService.execute(postId,GeneralConstants.COMMENT_TYPE,currentUser.getUsername());
		return new ResponseEntity<>(GeneralConstants.LIKED_MSG,HttpStatus.OK);
	}
	@GetMapping("/unlike/comment")
	public ResponseEntity<String> unlikeComment(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		DoUndoActionExecuteService unlikeService = new UndoActionImpl(likeUnlikeService);
		unlikeService.execute(postId,GeneralConstants.COMMENT_TYPE,currentUser.getUsername());
		return new ResponseEntity<>(GeneralConstants.LIKED_MSG,HttpStatus.OK);
	}
}
