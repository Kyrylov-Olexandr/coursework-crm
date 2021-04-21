package com.crm.dao;

import com.crm.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends DaoBase<User> {

    Optional<User> getByEmailAndPassword(String userEmail, String userPassword);

//    List findAllUsersWithOrders();
}
