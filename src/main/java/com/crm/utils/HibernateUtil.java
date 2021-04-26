package com.crm.utils;

import com.crm.entities.Order;
import com.crm.entities.OrderItem;
import com.crm.entities.Product;
import com.crm.entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(OrderItem.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("EXCEPTION: " + e);
            }
        }
        return sessionFactory;
    }
}

