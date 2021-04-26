package com.crm.service.impl;

import com.crm.dao.ProductDao;
import com.crm.dao.impl.ProductDaoImpl;
import com.crm.entities.Product;
import com.crm.service.ProductService;

import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductDao PRODUCT_DAO = new ProductDaoImpl(Product.class);

    @Override
    public Optional<Product> findById(int id) {
        return PRODUCT_DAO.findById(id);
    }
}
