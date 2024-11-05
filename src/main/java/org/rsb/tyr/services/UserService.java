package org.rsb.tyr.services;

import org.rsb.tyr.models.User;
import org.rsb.tyr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  @Autowired private UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public Optional<User> getUserByName(String name) {
    return repository.findByName(name);
  }
}
