package com.group15.Webspresso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group15.Webspresso.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
