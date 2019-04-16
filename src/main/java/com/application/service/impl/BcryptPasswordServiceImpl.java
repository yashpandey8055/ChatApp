package com.application.service.impl;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.application.service.PasswordService;

@Component(value="Bcrypt")
public class BcryptPasswordServiceImpl implements PasswordService{

	@Override
	public String encrypt(String plainPassword) {
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	}

	@Override
	public boolean match(String hashedPassword,String plainPassword) {
		return BCrypt.checkpw(hashedPassword, plainPassword);
	}

}
