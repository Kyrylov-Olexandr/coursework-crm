package com.crm.service.impl;

import com.crm.dao.OrderDao;
import com.crm.dao.impl.OrderDaoImpl;
import com.crm.entities.Order;
import com.crm.entities.OrderItem;
import com.crm.service.OrderService;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderDao ORDER_DAO = new OrderDaoImpl(Order.class);

    @Override
    public void save(Order order) {
        ORDER_DAO.save(order);
    }

    @Override
    public List<Order> findAll() {
        return ORDER_DAO.findAll();
    }

    @Override
    public Optional<Order> findById(int id) {
        return ORDER_DAO.findById(id);
    }

    @Override
    public void delete(Order order) {
        ORDER_DAO.delete(order);
    }

    @Override
    public void update(Order order) {
        ORDER_DAO.update(order);
    }

    @Override
    public BigDecimal getOrderItemsTotalSum(Order order) {
        List<BigDecimal> orderItemSum = new ArrayList<>();
        order.getOrderItems().forEach(orderItem -> orderItemSum.add(orderItem.getSum()));
        return orderItemSum.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
