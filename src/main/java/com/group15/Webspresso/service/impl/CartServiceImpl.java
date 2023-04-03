package com.group15.Webspresso.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group15.Webspresso.entity.Cart;
import com.group15.Webspresso.entity.CartItem;
import com.group15.Webspresso.entity.Product;
import com.group15.Webspresso.repository.CartItemRepository;
import com.group15.Webspresso.repository.CartRepository;
import com.group15.Webspresso.service.CartService;

import jakarta.servlet.http.HttpSession;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart getCurrentCart(HttpSession session) {
        Cart cart;
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            // Cart ID is stored in the session, retrieve the cart from the database
            cart = cartRepository.findById(cartId).orElse(null);
        } else {
            // Cart ID is not stored in the session, create a new cart and store its ID in
            // the session
            cart = new Cart();
            cartRepository.save(cart);
            session.setAttribute("cartId", cart.getId());
        }
        return cart;
    }

    @Override
    public void addProduct(Product product, int quantity, HttpSession session) {
        Cart cart = getCurrentCart(session);

        List<CartItem> cartItems = cart.getCartItems();
        Optional<CartItem> itemOptional = cartItems.stream()
                .filter(item -> {
                    Integer productId = item.getProduct().getId();
                    return productId != null && productId.equals(product.getId());
                })
                .findFirst();

        if (itemOptional.isPresent()) {
            CartItem item = itemOptional.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getCartItems().add(item);
        }

        cartItemRepository.saveAll(cart.getCartItems());
    }
}
