package com.crm.service;

import com.crm.entities.Product;
import com.crm.entities.User;

import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(int id);
}
