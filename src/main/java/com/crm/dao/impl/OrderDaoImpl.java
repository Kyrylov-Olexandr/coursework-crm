package com.crm.dao.impl;

import com.crm.dao.OrderDao;
import com.crm.entities.Order;
import com.crm.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDaoImpl extends DaoBaseImpl<Order> implements OrderDao {

    public OrderDaoImpl(Class<Order> type) {
        super(type);
    }

//    @Override
//    public List<Order> findAll() {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Query query = session.createQuery("SELECT order_product.quantity FROM p" + );
//        return super.findAll();
//    }
}
