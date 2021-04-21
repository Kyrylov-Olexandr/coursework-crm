package com.crm.dao.impl;

import com.crm.dao.ProductDao;
import com.crm.entities.Product;

public class ProductDaoImpl extends DaoBaseImpl<Product> implements ProductDao {
    public ProductDaoImpl(Class<Product> type) {
        super(type);
    }
}
