package com.crm.dao.impl;

import com.crm.dao.DaoBase;
import com.crm.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public abstract class DaoBaseImpl<E> implements DaoBase<E> {

    private final Class<E> type;

    public DaoBaseImpl(Class<E> type) {
        this.type = type;
    }

    @Override
    public Optional<E> findById(int id) {
        return Optional.of(HibernateUtil.getSessionFactory().openSession().get(type, id));
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
    public void saveOrUpdate(E e) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(e);
        tx1.commit();
        session.close();
    }


    @Override
    public void update(E e) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(e);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(E e) {
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(e);
        tx1.commit();
        session.close();
    }


    @Override
    public List<E> findAll() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM " + type.getSimpleName());
            return query.list();
        }
    }
}
