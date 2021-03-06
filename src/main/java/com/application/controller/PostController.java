package com.application.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.PostResponse;
import com.application.service.dao.CommentDao;
import com.application.service.dao.LikesDao;
import com.application.service.dao.PostDao;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.CommentDocument;
import com.application.service.dao.documents.LikeDocument;
import com.application.service.dao.documents.PostDocument;
import com.application.service.dao.documents.UserDocument;
import com.application.utils.Utils;
import com.mongodb.MongoException;

@RestController
@RequestMapping("/posts")
public class PostController {
	private static final Logger LOG = LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	LikesDao likesDao;
	
	@Autowired
	UsersDao userDao;
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	NotificationController notification;

	@PostMapping("/insert")
	public ResponseEntity<PostResponse> insertPost(@AuthenticationPrincipal UserDocument currentUser, @RequestBody PostDocument post){
		post.setUserName(currentUser.getUsername());
		post.setCreationDate(new Date());
		post.setUpdationDate(new Date());
		UserDocument user = userDao.find(currentUser.getUsername());
		user.getPosts().add(postDao.insert(post));
		user.setPosts(user.getPosts());
		userDao.save(user);
		PostResponse response = new PostResponse();
		response.setPost(post);
		response.setUser(user);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/getPost")
	public ResponseEntity<PostResponse> getPost(@AuthenticationPrincipal UserDocument currentUser, @RequestParam String postId){
			PostDocument post = postDao.findList("_id", postId);
			if(post==null) {
				CommentDocument commentDocument = commentDao.findOne("_id", postId);
				post = postDao.findList("_id",commentDocument.getPostId());
				postId = commentDocument.getPostId();
			}
			LikeDocument postLikeDocument = likesDao.getLikePostByQuery(Query.query(Criteria.where("postId").is(post.getId())));
			PostResponse postResponse = new PostResponse();
			postResponse.setDaysAgo(Utils.calculateTimeDifference(post.getCreationDate()));
			postResponse.setLikedByUser(postLikeDocument!=null?postLikeDocument.getLikedBy().contains(currentUser.getUsername()):false);
			postResponse.setLikesCount(postLikeDocument!=null?postLikeDocument.getLikedBy().size():0);
			postResponse.setUser(userDao.find(post.getUserName()));
			List<CommentDocument> comments = commentDao.find(post.getId());
			comments.stream().forEach(x->{x.setDaysAgo(Utils.calculateTimeDifference(x.getCreationDate()));
			LikeDocument commentLikeDocument = likesDao.getLikePostByQuery(Query.query(Criteria.where("postId").is(x.getId())));
				x.setLikedByUser(commentLikeDocument!=null?commentLikeDocument.getLikedBy().contains(currentUser.getUsername()):false);
			});
			postResponse.setComments(comments);
			postResponse.setPost(post);

		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
	@GetMapping("/like")
	public ResponseEntity<String> likePost(@AuthenticationPrincipal UserDocument currentUser, @RequestParam String postId){
		LikeDocument document = likesDao.getLikePost(postId);
		try{
		if(document!=null) {
			document.getLikedBy().add(currentUser.getUserName());
			document.setLikedBy(document.getLikedBy());
		}else {
			List<String> likes = new ArrayList<>(1);
			likes.add(currentUser.getUsername());
			document = new LikeDocument();
			document.setType("post");
			document.setLikedBy(likes);
			document.setPostId(postId);
		}
		likesDao.saveLikePost(document);
		notification.sendNotification(postDao.findOne("_id", postId).getUserName(),NotificationController.Action.POST_LIKE.name(),postId, currentUser.getUsername());
		return new ResponseEntity<>("Success",HttpStatus.OK);
	}catch(MongoException e) {
		LOG.error("Erro while liking comment with id "+postId+" by user "+currentUser.getUsername(),e);
	}
		return new ResponseEntity<>("Fail",HttpStatus.OK);
	}
	@GetMapping("/unlike")
	public ResponseEntity<String> unlikePost(@AuthenticationPrincipal UserDocument currentUser, @RequestParam String postId){
		LikeDocument document = likesDao.getLikePost(postId);
		try{
			if(document!=null) {
				document.getLikedBy().remove(currentUser.getUserName());
				document.setLikedBy(document.getLikedBy());
				likesDao.saveLikePost(document);
			}
			return new ResponseEntity<>("Success",HttpStatus.OK);
		}catch(MongoException e) {
			LOG.error("Erro while unliking comment with id "+postId+" by user "+currentUser.getUsername(),e);
		}
		return new ResponseEntity<>("fail",HttpStatus.OK);
	}
}
