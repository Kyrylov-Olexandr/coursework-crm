package com.crm.dao;

import java.util.List;
import java.util.Optional;

public interface DaoBase<T> {
    Optional<T> findById(int id);

    void save(T t);

    void update(T t);

    void delete(T t);

    List<T> findAll();
}
