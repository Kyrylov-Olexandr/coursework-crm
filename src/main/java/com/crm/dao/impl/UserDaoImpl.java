package com.crm.dao.impl;

import com.crm.dao.UserDao;
import com.crm.entities.Order;
import com.crm.entities.User;
import com.crm.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends DaoBaseImpl<User> implements UserDao {

    public UserDaoImpl(Class<User> type) {
        super(type);
    }

    @Override
    public Optional<User> getByEmailAndPassword(String userEmail, String userPassword) {
        String queryString = "SELECT user FROM User user WHERE email = :email AND password = :password";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<User> query = session.createQuery(queryString, User.class);
        query.setParameter("email", userEmail);
        query.setParameter("password", userPassword);
        List<User> resultList = query.getResultList();
        return resultList.isEmpty()
                ? Optional.empty()
                : Optional.of(resultList.get(0));
    }


}

