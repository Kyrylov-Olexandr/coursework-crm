package com.crm.dao;

import com.crm.entities.Order;
import com.crm.entities.User;

import java.util.Optional;

public interface UserDao extends DaoBase<User> {
    Order findOrderById(int userId);

    Optional<User> getByEmailAndPassword(String userEmail, String userPassword);
}
