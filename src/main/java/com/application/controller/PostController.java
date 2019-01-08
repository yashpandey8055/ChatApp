package com.application.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.application.bean.PostResponse;
import com.application.service.dao.LikesDao;
import com.application.service.dao.PostDao;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.LikeDocument;
import com.application.service.dao.documents.PostDocument;
import com.application.service.dao.documents.UserDocument;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	LikesDao likesDao;
	
	@Autowired
	UsersDao userDao;

	@PostMapping("/insert")
	public ResponseEntity<PostResponse> insertPost(@AuthenticationPrincipal UserDocument currentUser, @RequestBody PostDocument post){
		post.setUserName(currentUser.getUsername());
		UserDocument user = userDao.find(currentUser.getUsername());
		user.getPosts().add(postDao.insert(post));
		user.setPosts(user.getPosts());
		userDao.save(user);
		PostResponse response = new PostResponse();
		response.setPost(post);
		response.setUser(user);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("/like")
	public ResponseEntity<String> likePost(@AuthenticationPrincipal UserDocument currentUser, @RequestParam String postId){
		LikeDocument document = likesDao.getLikePost(postId);
		if(document!=null) {
			document.getLikedBy().add(currentUser.getUserName());
			document.setLikedBy(document.getLikedBy());
			document.setNetCount(document.getNetCount()+1);
		}else {
			List<String> likes = new ArrayList<>();
			likes.add(currentUser.getUsername());
			document = new LikeDocument();
			document.setLikedBy(likes);
			document.setNetCount(1);
			document.setPostId(postId);
		}
		likesDao.saveLikePost(document);
		return new ResponseEntity<>("Success",HttpStatus.OK);
		
	}
	@GetMapping("/unlike")
	public ResponseEntity<String> unlikePost(@AuthenticationPrincipal UserDocument currentUser, @RequestParam String postId){
		LikeDocument document = likesDao.getLikePost(postId);
		if(document!=null) {
			document.getLikedBy().remove(currentUser.getUserName());
			document.setLikedBy(document.getLikedBy());
			document.setNetCount(document.getNetCount()-1);
			likesDao.saveLikePost(document);
		}
		return new ResponseEntity<>("fail",HttpStatus.OK);
		
	}
}
