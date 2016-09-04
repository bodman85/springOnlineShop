package com.mkyong.form.dao;

import java.util.List;

import com.mkyong.form.model.Product;

public interface ProductDao {

    Product findById(Integer id);

    List<Product> findAll();

    void save(Product product);

    void update(Product product);

    void delete(Integer id);

}