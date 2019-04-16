package com.application.service;

public interface PasswordService {

	public String encrypt(String plainPassword);
	
	public boolean match(String hashedPassword,String plainPassword);
}
