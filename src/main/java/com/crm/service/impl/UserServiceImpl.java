package com.crm.service.impl;

import com.crm.dao.UserDao;
import com.crm.dao.impl.UserDaoImpl;
import com.crm.entities.User;
import com.crm.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao USER_DAO = new UserDaoImpl();

    @Override
    public Optional<User> getByEmailAndPassword(String email, String password) {
        return USER_DAO.getByEmailAndPassword(email, password);
    }

    @Override
    public void register(User user) {
        USER_DAO.save(user);
    }

    @Override
    public List<User> findAll() {
        return USER_DAO.findAll();
    }

}
