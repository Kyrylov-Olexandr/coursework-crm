package com.crm.service;

import com.crm.entities.Order;
import com.crm.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(int id);

    Optional<User> getByEmailAndPassword(String email, String password);

    void register(User user);

    List<User> findAll();

}
