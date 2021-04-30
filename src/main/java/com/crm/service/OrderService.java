package com.crm.service;

import com.crm.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void save(Order order);

    List<Order> findAll();

    Optional<Order> findById(int id);

    void delete(Order order);

    void update(Order order);

}
