package com.crm.service;

import com.crm.entities.Order;
import com.crm.entities.Product;
import com.crm.entities.User;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(int id);

    List<Product> findAll();

    void delete(Product product);

    void update(Product product);
}
