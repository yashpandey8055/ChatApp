package com.application.service.likeunlikeservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.service.ILikeUnlikeAction;
import com.application.service.LikeUnlikeExecuteService;

@Service("LikeAction")
public class LikeActionImpl implements LikeUnlikeExecuteService{
	
	
	@Autowired
	public LikeActionImpl(ILikeUnlikeAction likeUnlikeService) {
		super();
		this.likeUnlikeService = likeUnlikeService;
	}

	ILikeUnlikeAction likeUnlikeService;

	@Override
	public void execute(String postId,String type,String username) {
		likeUnlikeService.likePost(postId,type,username);
		
	}

}
