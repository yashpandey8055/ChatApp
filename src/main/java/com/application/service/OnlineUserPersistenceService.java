package com.application.service;

public interface OnlineUserPersistenceService {

	public void save(String id);
	
	public String fetch(String id);
	
	public void remove(String id);
}
