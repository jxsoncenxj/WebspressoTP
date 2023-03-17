package com.group15.Webspresso.service;

import java.util.List;

import com.group15.Webspresso.entity.Order;

public interface OrderService {

    List<Order> getOrdersForUser(int userId);

    List<Order> getAllOrders();

}
