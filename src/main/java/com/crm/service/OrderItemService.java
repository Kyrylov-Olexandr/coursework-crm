package com.crm.service;

import com.crm.entities.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItem> findAll();

    Optional<OrderItem> findById(int id);

    void delete(OrderItem orderItem);

    void update(OrderItem orderItem);

}
