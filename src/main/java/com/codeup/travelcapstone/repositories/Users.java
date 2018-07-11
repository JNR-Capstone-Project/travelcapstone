package com.codeup.travelcapstone.repositories;

import com.codeup.travelcapstone.model.User;
import org.springframework.data.repository.CrudRepository;

public interface Users extends CrudRepository<User, Long> {
    User findByUsername(String username);

  User findUsersById(long id);
}