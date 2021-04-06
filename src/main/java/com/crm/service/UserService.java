package com.crm.service;

import com.crm.model.User;

public interface UserService {
    boolean login(String email, String password);

    boolean register(User user);
}
