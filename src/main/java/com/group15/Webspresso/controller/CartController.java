package com.group15.Webspresso.controller;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group15.Webspresso.entity.Cart;
import com.group15.Webspresso.entity.CartItem;
import com.group15.Webspresso.entity.Product;
import com.group15.Webspresso.entity.Order;
import com.group15.Webspresso.entity.OrderStatus;
import com.group15.Webspresso.entity.User;
import com.group15.Webspresso.repository.CartItemRepository;
import com.group15.Webspresso.repository.CartRepository;
import com.group15.Webspresso.repository.ProductRepository;
import com.group15.Webspresso.repository.OrderRepository;
import java.time.LocalDateTime;
import com.group15.Webspresso.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam int productId, @RequestParam int quantity, HttpServletRequest request) {
        // Retrieve the product from the database
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            // Product with the given ID does not exist
            return "redirect:/products";
        }
        Product product = productOptional.get();

        // Retrieve the cart for the current user
        Cart cart = cartService.getCurrentCart(request.getSession());

        // Check if the product is already in the cart
        Optional<CartItem> cartItemOptional = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (cartItemOptional.isPresent()) {
            // Product is already in the cart, increase the quantity
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            // Product is not in the cart, add it as a new cart item
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpServletRequest request) {
        // Retrieve the cart for the current user
        Cart cart = cartService.getCurrentCart(request.getSession());
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(@ModelAttribute("cartItems") List<CartItem> cartItems) {
        cartItemRepository.saveAll(cartItems);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/empty")
    public String emptyCart(HttpServletRequest request) {
        // Retrieve the cart for the current user
        Cart cart = cartService.getCurrentCart(request.getSession());
        cart.clear();
        return "redirect:/cart";
    }

    // @PostMapping("/cart/checkout")
    // public String checkout(HttpServletRequest request, Authentication authentication) {
    // // Retrieve the cart for the current user
    // Cart cart = cartService.getCurrentCart(request.getSession());
    // User user = authentication.getCurrentUser();

    // // Calculate the total price of the items in the cart
    // double totalPrice = 0;
    // for (CartItem item : cart.getCartItems()) {
    //     totalPrice += item.getProduct().getProductPrice() * item.getQuantity();
    // }

    // // Create a new order
    // Order order = new Order();
    // order.setUser(user);
    // order.setCart(cart);
    // order.setStatus(new OrderStatus());
    // order.setTotalPrice(totalPrice);
    // order.setTimestamp(LocalDateTime.now());
    // orderRepository.save(order);

    // // Clear the cart
    // cart.clear();

    

    // return "checkout";
    // }
}