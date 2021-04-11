package com.crm.dao;

import com.crm.models.Order;
import com.crm.models.User;

import java.util.List;

public interface UserDaoInterface {
    User findById(int userId);

    void save(User user);

    void update(User user);

    void delete(User user);

    Order findOrderById(int userId);
    List<User> findAll();
}
