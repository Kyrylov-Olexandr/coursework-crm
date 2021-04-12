package com.crm.service;

import com.crm.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> login(String email, String password);

    void register(User user);
}
