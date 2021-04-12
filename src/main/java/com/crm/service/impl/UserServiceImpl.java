package com.crm.service.impl;

import com.crm.dao.UserDao;
import com.crm.dao.impl.UserDaoImpl;
import com.crm.entities.User;
import com.crm.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public Optional<User> login(String email, String password) {
        return userDao.getByEmailAndPassword(email, password);
    }

    @Override
    public void register(User user) {
        userDao.save(user);
    }
}
