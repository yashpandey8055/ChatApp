package com.application.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.CommentInsertBean;
import com.application.bean.MessageBean;
import com.application.service.dao.CommentDao;
import com.application.service.dao.LikesDao;
import com.application.service.dao.PostDao;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.CommentDocument;
import com.application.service.dao.documents.LikeDocument;
import com.application.service.dao.documents.PostDocument;
import com.application.service.dao.documents.UserDocument;
import com.mongodb.MongoException;

@RestController
@RequestMapping("/comment")
public class CommentController {
	private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);

	
	@Autowired
	PostDao postDao;
	
	@Autowired
	LikesDao likesDao;
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	UsersDao userDao;

	@PostMapping("/insert")
	public ResponseEntity<CommentDocument> insertPost(@AuthenticationPrincipal UserDocument currentUser, @RequestBody CommentInsertBean comment){
		PostDocument post = postDao.findOne("_id",comment.getPostId());
		CommentDocument commentDocument = new CommentDocument();
		commentDocument.setLikes(0);
		commentDocument.setReplies(new ArrayList<>()); 
		commentDocument.setRepliesCount(0);
		commentDocument.setMessage(comment.getComment());
		commentDocument.setUserName(comment.getUserName());
		commentDocument.setProfileUrl(currentUser.getProfileUrl());
		commentDocument.setPostId(post.getId());
		commentDocument.setIsStatus(post.isIsStatus());
		commentDocument.setCreationDate(new Date());
		commentDocument.setUpdationDate(new Date());
		commentDao.insert(commentDocument);
		post.getComments().add(commentDao.insert(commentDocument));
		post.setComments(post.getComments());
		postDao.insert(post);
		
		MessageBean message = new MessageBean();
		message.setSender(currentUser.getUsername());
		message.setReceiver(post.getUserName());
		message.setCommentNotification(message.getSender(), comment.getComment());
		return new ResponseEntity<>(commentDocument,HttpStatus.OK);
		
	}
	
	@GetMapping("/like")
	public ResponseEntity<String> likePost(@AuthenticationPrincipal UserDocument currentUser, @RequestParam String postId){
		LikeDocument document = likesDao.getLikePost(postId);
		
		try {
		if(document!=null) {
			document.getLikedBy().add(currentUser.getUserName());
			document.setLikedBy(document.getLikedBy());
		}else {
			List<String> likes = new ArrayList<>(1);
			likes.add(currentUser.getUsername());
			document = new LikeDocument();
			document.setType("comment");
			document.setLikedBy(likes);
			document.setPostId(postId);
		}
		CommentDocument postDocument = commentDao.findOne("_id",document.getPostId());
		MessageBean message = new MessageBean();
		message.setSender(currentUser.getUsername());
		message.setReceiver(postDocument.getUserName());
		message.setLikeNotification(currentUser.getUsername());
		likesDao.saveLikePost(document);
		return new ResponseEntity<>("Success",HttpStatus.OK);
		}catch(MongoException e) {
			LOG.error("Erro while liking post with id "+postId+" by user "+currentUser.getUsername(),e);
		}
		
		return new ResponseEntity<>("fail",HttpStatus.OK);
		
	}
	@GetMapping("/unlike")
	public ResponseEntity<String> unlikePost(@AuthenticationPrincipal UserDocument currentUser, @RequestParam String postId){
		LikeDocument document = likesDao.getLikePost(postId);
		try {
		if(document!=null) {
			document.getLikedBy().remove(currentUser.getUserName());
			document.setLikedBy(document.getLikedBy());
			likesDao.saveLikePost(document);
			return new ResponseEntity<>("success",HttpStatus.OK);
		}
		}catch(MongoException e) {
			LOG.error("Erro while unliking post with id "+postId+" by user "+currentUser.getUsername(),e);
		}
		return new ResponseEntity<>("fail",HttpStatus.OK);
		
	}
}
