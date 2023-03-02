package com.group15.Webspresso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group15.Webspresso.entity.Checkout;
import com.group15.Webspresso.service.CheckoutService;
import com.group15.Webspresso.repository.CheckoutRepository;

@Service
public class CheckoutServiceImpl implements CheckoutService{
    @Autowired
    private CheckoutRepository checkoutRepository;

    public Checkout createCheckout(Checkout checkout) {
        return checkoutRepository.save(checkout);
    }

    public List<Checkout> getAllCheckouts() {
        return checkoutRepository.findAll();
    }
}
