package com.crm.dao.impl;

import com.crm.dao.UserDao;
import com.crm.entities.Order;
import com.crm.entities.User;
import com.crm.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

public class UserDaoImpl extends DaoBaseImpl<User> implements UserDao {


    @Override
    public Order findOrderById(int userId) {
        return HibernateUtil.getSessionFactory().openSession().get(Order.class, userId);
    }

    @Override
    public Optional<User> getByEmailAndPassword(String userEmail, String userPassword) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String queryString = "SELECT user FROM User user WHERE email = :email AND password = :password";
        Query<User> query = session.createQuery(queryString, User.class);
        query.setParameter("email", userEmail);
        query.setParameter("password", userPassword);
        return Optional.of(query.getSingleResult());
    }
}

