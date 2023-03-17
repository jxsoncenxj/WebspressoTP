package com.group15.Webspresso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.group15.Webspresso.entity.Cart;
import com.group15.Webspresso.entity.CartItem;
import com.group15.Webspresso.repository.CartItemRepository;
import com.group15.Webspresso.service.CartService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CheckoutController {
    
    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping("/checkout")
    public String viewCheckout(Model model, HttpServletRequest request, HttpSession session) {
        Cart cart = cartService.getCurrentCart(request.getSession());
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        model.addAttribute("cartItems", cartItems);
        session.setAttribute("cartItems", cartItems);
        return "checkout";
    }

}
