package com.group15.Webspresso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group15.Webspresso.entity.Product;

public interface ProductRepository extends JpaRepository <Product, Integer> {
    
}
