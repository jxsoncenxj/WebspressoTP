package com.group15.Webspresso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group15.Webspresso.entity.Order;
import com.group15.Webspresso.repository.OrderRepository;
import com.group15.Webspresso.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getOrdersForUser(int userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

}
