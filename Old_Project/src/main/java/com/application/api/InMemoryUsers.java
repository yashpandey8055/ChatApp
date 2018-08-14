package com.application.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.application.bean.User;

@Component
public class InMemoryUsers implements UserCrudService{
	Map<String, User> users = new HashMap<>();

	  @Override
	  public User save(final User user) {
	    return users.put(user.getId(), user);
	  }

	  @Override
	  public Optional<User> find(final String id) {
	    return Optional.ofNullable(users.get(id));
	  }

	  @Override
	  public Optional<User> findByUsername(final String username) {
	    return users
	      .values()
	      .stream()
	      .filter(u -> Objects.equals(username, u.getUsername()))
	      .findFirst();
	  }
}
