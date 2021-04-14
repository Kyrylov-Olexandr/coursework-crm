package com.crm.dao.impl;

import com.crm.dao.DaoBase;
import com.crm.entities.User;
import com.crm.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public abstract class DaoBaseImpl<E> implements DaoBase<E> {
    Class<E> persistentClass;

    @Override
    public Optional<E> findById(int userId) {
        return Optional.of(HibernateUtil.getSessionFactory().openSession().get(persistentClass, userId));
    }

    @Override
    public void save(E e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(e);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(E e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(e);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(E e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(e);
        tx1.commit();
        session.close();
    }


    @Override
    public List<E> findAll() {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        String query = "SELECT a FROM " + persistentClass.getName() + " a";
//        return session.createQuery(query, persistentClass).getResultList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM User");
        List users = query.list();
        return users;
    }
}
