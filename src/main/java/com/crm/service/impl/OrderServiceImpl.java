package com.crm.service.impl;

import com.crm.dao.OrderDao;
import com.crm.dao.impl.OrderDaoImpl;
import com.crm.entities.Order;
import com.crm.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDao ORDER_DAO = new OrderDaoImpl(Order.class);

    @Override
    public List<Order> findAll() {
        return ORDER_DAO.findAll();
    }
}
