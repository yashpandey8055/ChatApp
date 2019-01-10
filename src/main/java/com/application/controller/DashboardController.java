package com.application.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	PostDao postDao;
	
	
	@Autowired
	LikesDao likesDao;
	
	@Autowired
	UsersDao userDao;
	
	@Autowired
	CommentDao commentDao;

	@GetMapping("/getPosts")
	public ResponseEntity<List<PostResponse>> getPost(@AuthenticationPrincipal UserDocument currentUser){
		List<String> following = currentUser.getFollowing();
		following.add(currentUser.getUsername());
		
		List<PostResponse> response = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH,-3);
		Query query = Query.query(Criteria.where("userName").in(following).and("creationDate").gte(new Date(cal.getTimeInMillis())));
		List<UserDocument> users = userDao.findByQuery(Query.query(Criteria.where("userName").in(following)));
		
		Map<String,UserDocument> usersMap = users.stream().collect(Collectors.toMap(UserDocument::getUsername, it -> it));
		
		List<PostDocument> posts = postDao.findByQuery(query);
		
		for(PostDocument post:posts) {
			LikeDocument postLikeDocument = likesDao.getLikePostByQuery(Query.query(Criteria.where("postId").is(post.getId()).and("likedBy").all(currentUser.getUsername())));
			PostResponse postResponse = new PostResponse();
			postResponse.setDaysAgo(Utils.calculateTimeDifference(post.getCreationDate()));
			postResponse.setLikedByUser(postLikeDocument!=null);
			postResponse.setLikesCount(postLikeDocument!=null?postLikeDocument.getLikedBy().size():0);
			postResponse.setUser(usersMap.get(post.getUserName()));
			List<CommentDocument> comments = commentDao.find(post.getId());
			comments.stream().forEach(x->{x.setDaysAgo(Utils.calculateTimeDifference(x.getCreationDate()));
			LikeDocument commentLikeDocument = likesDao.getLikePostByQuery(Query.query(Criteria.where("postId").is(x.getId()).and("likedBy").all(currentUser.getUsername())));
				x.setLikedByUser(commentLikeDocument!=null);
			});
			postResponse.setComments(comments);
			postResponse.setPost(post);
			
			response.add(postResponse);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
		
		
	}
}
