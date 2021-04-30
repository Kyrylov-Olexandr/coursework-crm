package com.crm.service.impl;

import com.crm.dao.OrderItemDao;
import com.crm.dao.impl.OrderItemDaoImpl;
import com.crm.entities.Order;
import com.crm.entities.OrderItem;
import com.crm.service.OrderItemService;
import com.crm.service.OrderService;

import java.util.List;
import java.util.Optional;

public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemDao ORDER_ITEM_DAO = new OrderItemDaoImpl(OrderItem.class);

    @Override
    public List<OrderItem> findAll() {
        return ORDER_ITEM_DAO.findAll();
    }

    @Override
    public Optional<OrderItem> findById(int id) {
        return ORDER_ITEM_DAO.findById(id);
    }

    @Override
    public void delete(OrderItem orderItem) {
        ORDER_ITEM_DAO.delete(orderItem);
    }

    @Override
    public void update(OrderItem orderItem) {
        ORDER_ITEM_DAO.update(orderItem);
    }
}
