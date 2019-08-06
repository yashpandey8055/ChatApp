package com.application.service.action.doundoactionservice.impl;


import com.application.service.IDoUndoAction;
import com.application.service.DoUndoActionExecuteService;

public class UndoActionImpl implements DoUndoActionExecuteService{

	public UndoActionImpl(IDoUndoAction likeUnlikeService) {
		super();
		this.likeUnlikeService = likeUnlikeService;
	}

	IDoUndoAction likeUnlikeService;

	@Override
	public void execute(String postId,String type,String username) {
		likeUnlikeService.undoAction(postId,type,username);
	}

}
