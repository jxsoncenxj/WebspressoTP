package com.group15.Webspresso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group15.Webspresso.entity.Product;

public interface ProductRepository extends JpaRepository <Product, Integer> {

    List<Product> findByProductNameContaining(String searchText);

    List<Product> findByOrigin(String origin);
    
}
