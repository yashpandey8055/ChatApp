package com.application.service;

@FunctionalInterface
public interface LikeUnlikeExecuteService {
	
	public void execute(String postId,String type,String username);

}
