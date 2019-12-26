package com.application.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;
import com.application.bean.ViewPostBean;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.PostActivityReqResBean;
import com.application.request.response.bean.UpdatePostRequestResBean;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.DisplayPostService;
import com.application.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	@Qualifier("StatusPost")
	PostService postService;
	
	@Autowired
	
	@Qualifier("UserSpecific")
	DisplayPostService userPostService;

	@PostMapping("/insert")
	public ResponseEntity<GenericResponseBean> insertPost(@AuthenticationPrincipal User currentUser, @RequestBody PostActivityReqResBean postActivityReqResBean){
		postActivityReqResBean.setUserName(currentUser.getUsername());
		GenericResponseBean post = postService.post(postActivityReqResBean);
		return new ResponseEntity<>(post,HttpStatus.OK);
	}
	
	@GetMapping("/getPost")
	public ResponseEntity<Object> getPost(@AuthenticationPrincipal User currentUser, @RequestParam String postId){
		GenericResponseBean post = postService.view(postId,currentUser.getUsername());
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@GetMapping("/getuserpost/{username}")
	public ResponseEntity<GenericResponseBean> getPosts(@AuthenticationPrincipal User currentUser,@PathVariable("username") String user){			
		ViewPostBean viewPostBean = new ViewPostBean();
		viewPostBean.setActiveUser(currentUser);
		viewPostBean.setUsernameForPost(user);
		return new ResponseEntity<>(userPostService.viewPost(viewPostBean),HttpStatus.OK);
		
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<GenericResponseBean> updatePosts(@AuthenticationPrincipal User currentUser,@RequestBody UpdatePostRequestResBean updatePost){			
		 GenericResponseBean responseBean = new GenericResponseBean();
		if(currentUser.getUsername().equals(updatePost.getUserName())) {
			PostActivityReqResBean postActivityReqResBean = new PostActivityReqResBean();
			postActivityReqResBean.setStatus(updatePost.getStatus());
			postActivityReqResBean.setUserName(updatePost.getUserName());
			postActivityReqResBean.setId(updatePost.getPostId());
			responseBean.setData(postService.edit(postActivityReqResBean));
		}else {
			responseBean.setData(RequestResponseConstant.FAILURE_RESPONSE);
		}
 
		return new ResponseEntity<>(responseBean,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<GenericResponseBean> deletePost(@AuthenticationPrincipal User currentUser,@RequestBody UpdatePostRequestResBean updatePost){			
		 GenericResponseBean responseBean = new GenericResponseBean();
		if(currentUser.getUsername().equals(updatePost.getUserName())) {
			responseBean.setData(postService.delete(updatePost.getPostId()));
		}else {
			responseBean.setData(RequestResponseConstant.FAILURE_RESPONSE);
		}
 
		return new ResponseEntity<>(responseBean,HttpStatus.OK);
		
	}
}
