package com.crm.dao;

import com.crm.model.User;

public interface UserDao {
    boolean add(User user);
//    boolean remove(User user);
//    boolean update(User user);
    User get(int id);
}
