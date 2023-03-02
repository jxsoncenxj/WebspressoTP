package com.group15.Webspresso.service;

import java.util.List;

import com.group15.Webspresso.entity.Checkout;

public interface CheckoutService {
    
    Checkout createCheckout(Checkout checkout);

    List<Checkout> getAllCheckouts();
}
