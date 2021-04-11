package com.crm.service;

import com.crm.models.User;

public interface UserService {
    boolean login(String email, String password);

    boolean register(User user);
}
