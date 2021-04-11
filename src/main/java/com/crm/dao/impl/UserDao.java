package com.crm.dao.impl;

import com.crm.dao.UserDaoInterface;
import com.crm.models.Order;
import com.crm.models.User;
import com.crm.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao implements UserDaoInterface {

    @Override
    public User findById(int userId) {
        return HibernateUtil.getSessionFactory().openSession().get(User.class, userId);
    }

    @Override
    public void save(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    @Override
    public Order findOrderById(int userId) {
        return HibernateUtil.getSessionFactory().openSession().get(Order.class, userId);
    }

    public List<User> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("SELECT a FROM User a", User.class).getResultList();
    }
}
