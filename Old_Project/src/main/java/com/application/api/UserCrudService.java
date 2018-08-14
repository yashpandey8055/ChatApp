package com.application.api;

import java.util.Optional;

import com.application.bean.User;

public interface UserCrudService {

	User save(User user);

	  Optional<User> find(String id);

	  Optional<User> findByUsername(String username);
}
