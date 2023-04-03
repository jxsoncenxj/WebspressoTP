package com.group15.Webspresso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group15.Webspresso.classes.OrderStatus;
import com.group15.Webspresso.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(int userId);
    // List<Order> findByStatus(OrderStatus status);

    List<Order> findByOrderStatus(OrderStatus valueOf);


}
