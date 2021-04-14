package com.crm.service;

import com.crm.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getByEmailAndPassword(String email, String password);

    void register(User user);

    List<User> findAll();
}
