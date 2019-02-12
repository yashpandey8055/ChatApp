package com.application.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import com.application.config.UUIDAuthenticationService;
import com.application.service.OnlineUserPersistenceService;
import com.application.service.dao.CommentDao;
import com.application.service.dao.LikesDao;
import com.application.service.dao.PostDao;
import com.application.service.dao.UsersDao;
import com.application.service.dao.documents.CommentDocument;
import com.application.service.dao.documents.LikeDocument;
import com.application.service.dao.documents.PostDocument;
import com.application.service.dao.documents.UserDocument;
import com.application.utils.Utils;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UsersDao userDao;
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	LikesDao likesDao;
	
	@Autowired
	OnlineUserPersistenceService onlineUser;
	
	UUIDAuthenticationService authentication;
	
	@GetMapping("/follow/{user}")
	public ResponseEntity<UserDocument> followUser(@AuthenticationPrincipal final UserDocument currentUser,@PathVariable("user") String userName){
		UserDocument currentUserDocument = userDao.find(currentUser.getUsername());
		UserDocument followedUserDocument = userDao.find(userName);
		currentUserDocument.getFollowing().add(userName);
		currentUserDocument.setFollowing(currentUserDocument.getFollowing());
		followedUserDocument.setFollowers(followedUserDocument.getFollowers()+1);
		userDao.save(currentUserDocument);
		userDao.save(followedUserDocument);
		return new ResponseEntity<>(followedUserDocument,HttpStatus.ACCEPTED);
		
	}

	@GetMapping("/unfollow/{user}")
	public ResponseEntity<UserDocument> unfollowUser(@AuthenticationPrincipal final UserDocument currentUser,@PathVariable("user") String userName){
		UserDocument currentUserDocument = userDao.find(currentUser.getUsername());
		UserDocument followedUserDocument = userDao.find(userName);
		currentUserDocument.getFollowing().remove(userName);
		currentUserDocument.setFollowing(currentUserDocument.getFollowing());
		followedUserDocument.setFollowers(followedUserDocument.getFollowers()-1);
		userDao.save(currentUserDocument);
		userDao.save(followedUserDocument);
		return new ResponseEntity<>(followedUserDocument,HttpStatus.ACCEPTED);
		
	}

	  @GetMapping("/current")
	  public UserDocument getCurrent(@AuthenticationPrincipal final UserDocument user) {
	    return user;
	  }

	  @GetMapping("/connected")
	  public Collection<UserDocument> connectedNotification(@AuthenticationPrincipal final UserDocument currentUser) {
		 return  onlineUser.getAllUsers();
	  }
	
	  @GetMapping("/follow/isfollowing/{user}")
	  public ResponseEntity<Boolean> isFollwoing(@AuthenticationPrincipal final UserDocument currentUser,@PathVariable("user") String userName) {
		  UserDocument currentUserDocument = userDao.find(currentUser.getUsername());
		  if(currentUserDocument.getFollowing().contains(userName)) {
			  return new ResponseEntity<>(true,HttpStatus.OK);
		  }
		  return new ResponseEntity<>(false,HttpStatus.OK);
	  }
	  
	  @GetMapping("/user/{user}")
	  public ResponseEntity<UserDocument> getUser(@AuthenticationPrincipal final UserDocument currentUser,@PathVariable("user") String user) {
		  return new ResponseEntity<>(userDao.find(user),HttpStatus.OK);
	  }
	  
	  @PutMapping("/user/update")
	  public ResponseEntity<UserDocument> updateUser(@AuthenticationPrincipal final UserDocument currentUser,@RequestBody UserDocument updateUser) {
		  updateUser.setId(currentUser.getId());
		  userDao.save(updateUser);
		  return new ResponseEntity<>(updateUser,HttpStatus.OK);
	  }
	  
		@GetMapping("/getPosts/{user}")
		public ResponseEntity<List<PostResponse>> getPosts(@AuthenticationPrincipal UserDocument currentUser,@PathVariable("user") String user){			
			List<PostResponse> response = new ArrayList<>();
			Query query = Query.query(Criteria.where("userName").is(user));
			
			List<PostDocument> posts = postDao.findByQuery(query);
			
			for(PostDocument post:posts) {
				LikeDocument postLikeDocument = likesDao.getLikePostByQuery(Query.query(Criteria.where("postId").is(post.getId()).and("likedBy").all(currentUser.getUsername())));
				PostResponse postResponse = new PostResponse();
				postResponse.setDaysAgo(Utils.calculateTimeDifference(post.getCreationDate()));
				postResponse.setLikedByUser(postLikeDocument!=null);
				postResponse.setLikesCount(postLikeDocument!=null?postLikeDocument.getLikedBy().size():0);
				postResponse.setUser(getUser(currentUser, user).getBody());
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
