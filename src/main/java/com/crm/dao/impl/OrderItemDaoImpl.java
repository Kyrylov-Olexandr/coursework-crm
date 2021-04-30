package com.crm.dao.impl;

import com.crm.dao.OrderItemDao;
import com.crm.entities.OrderItem;

public class OrderItemDaoImpl extends DaoBaseImpl<OrderItem> implements OrderItemDao {
    public OrderItemDaoImpl(Class<OrderItem> type) {
        super(type);
    }
}
