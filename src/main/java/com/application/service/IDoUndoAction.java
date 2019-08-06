package com.application.service;

public interface IDoUndoAction {
	
	public void doAction(String id, String type,String username) ;
	
	public void undoAction(String id, String type,String username);
}
