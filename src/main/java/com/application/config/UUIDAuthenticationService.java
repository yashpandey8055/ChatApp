package com.application.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.application.bean.UserDocument;
import com.application.service.UserCrudService;

@Component
public class UUIDAuthenticationService {
	  @Autowired
	  UserCrudService users;

	  public Optional<String> login(final String username) {
	    final String uuid = UUID.randomUUID().toString();
	    
	    users.save(uuid,username);
	    return Optional.of(uuid);
	  }

	  public UserDocument findByToken(final String token) {
	    return users.find(token);
	  }

	
}
