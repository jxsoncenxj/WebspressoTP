package com.group15.Webspresso.service;

import com.group15.Webspresso.entity.Cart;
import com.group15.Webspresso.entity.Product;

import jakarta.servlet.http.HttpSession;

public interface CartService {
    Cart getCurrentCart(HttpSession session);

    void addProduct(Product product, int quantity, HttpSession session);
}
