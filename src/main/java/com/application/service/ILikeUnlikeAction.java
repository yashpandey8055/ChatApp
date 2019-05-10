package com.application.service;

public interface ILikeUnlikeAction {
	
	public void likePost(String id, String type,String username) ;
	
	public void unlikePost(String id, String type,String username);
}
