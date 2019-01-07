package com.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.PostResponse;
import com.application.service.dao.CommentDao;
import com.application.service.dao.PostDao;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.CommentDocument;
import com.application.service.dao.documents.PostDocument;
import com.application.service.dao.documents.UserDocument;
import com.application.utils.Utils;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	UsersDao userDao;
	
	@Autowired
	CommentDao commentDao;

	@GetMapping("/getPosts")
	public ResponseEntity<List<PostResponse>> getPost(@AuthenticationPrincipal UserDocument currentUser){
		List<String> following = currentUser.getFollowing();
		following.add(currentUser.getUsername());
		List<PostResponse> response = new ArrayList<>();
		for(String user: following) {
			List<PostDocument> list = postDao.findByOneUserName(user);
			for(PostDocument post:list) {
				PostResponse postResponse = new PostResponse();
				postResponse.setUser(userDao.find(user));
				List<CommentDocument> comments = commentDao.find(post.getId());
						comments.stream()
						.forEach(x->x.setDaysAgo(Utils.calculateTimeDifference(x.getDate())));
				postResponse.setComments(comments);
				postResponse.setPost(post);
				
				response.add(postResponse);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
		
		
	}
}
