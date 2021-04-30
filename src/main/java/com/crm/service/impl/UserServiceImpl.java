package com.crm.service.impl;

import com.crm.dao.OrderDao;
import com.crm.dao.UserDao;
import com.crm.dao.impl.OrderDaoImpl;
import com.crm.dao.impl.UserDaoImpl;
import com.crm.entities.Order;
import com.crm.entities.User;
import com.crm.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao USER_DAO = new UserDaoImpl(User.class);

    @Override
    public Optional<User> findById(int id) {
        return USER_DAO.findById(id);
    }

    @Override
    public Optional<User> getByEmailAndPassword(String email, String password) {
        return USER_DAO.getByEmailAndPassword(email, password);
    }

    @Override
    public void register(User user) {
        USER_DAO.save(user);
    }

    @Override
    public void update(User user) {
        USER_DAO.update(user);
    }

    @Override
    public List<User> findAll() {
        return USER_DAO.findAll();
    }


}
