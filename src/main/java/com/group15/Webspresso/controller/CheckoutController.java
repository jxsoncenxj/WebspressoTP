package com.group15.Webspresso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group15.Webspresso.entity.Checkout;
import com.group15.Webspresso.service.CheckoutService;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public Checkout createCheckout(@RequestBody Checkout checkout) {
        return checkoutService.createCheckout(checkout);
    }

    @GetMapping
    public List<Checkout> getAllCheckouts() {
        return checkoutService.getAllCheckouts();
    }

    @PostMapping("/api/checkout")
    public Checkout checkout(@RequestParam long orderId, @RequestParam double totalPrice) {
        // TODO: Retrieve user ID from authenticated user
        int userId = 1; // Replace with actual user ID
        Checkout checkout = new Checkout();
        checkout.setUserId(userId);
        checkout.setOrderId(orderId);
        checkout.setTotalPrice(totalPrice);
        return checkoutService.createCheckout(checkout);
    }

    // Other endpoints for retrieving checkouts by user ID, order ID, etc.
}
