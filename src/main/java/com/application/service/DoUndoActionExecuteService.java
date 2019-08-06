package com.application.service;

@FunctionalInterface
public interface DoUndoActionExecuteService {
	
	public void execute(String postId,String type,String username);

}
