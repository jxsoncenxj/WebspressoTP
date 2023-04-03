package com.group15.Webspresso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group15.Webspresso.classes.OrderStatus;
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

    @Override
    public Order getOrderByID(long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrders(String status) {
        if (status != null && !status.isEmpty()) {
            return orderRepository.findByOrderStatus(OrderStatus.valueOf(status));
        } else {
            return orderRepository.findAll();
        }
    }

}
