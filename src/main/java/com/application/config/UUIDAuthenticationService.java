package com.application.config;


import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.application.api.UserAuthenticationService;
import com.application.api.UserCrudService;
import com.application.bean.User;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class UUIDAuthenticationService implements UserAuthenticationService {
  @Autowired
  UserCrudService users;

  @Override
  public Optional<String> login(final String username, final String password) {
    final String uuid = UUID.randomUUID().toString();
    final User user = User
      .builder()
      .id(uuid)
      .username(username)
      .password(password)
      .build();

    users.save(user);
    return Optional.of(uuid);
  }

  @Override
  public Optional<User> findByToken(final String token) {
    return users.find(token);
  }

  @Override
  public void logout(final User user) {

  }
}
