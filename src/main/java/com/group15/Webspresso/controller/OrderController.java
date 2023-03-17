package com.group15.Webspresso.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group15.Webspresso.entity.Cart;
import com.group15.Webspresso.entity.CartItem;
import com.group15.Webspresso.entity.Order;
import com.group15.Webspresso.entity.OrderItem;
import com.group15.Webspresso.entity.User;
import com.group15.Webspresso.repository.CartItemRepository;
import com.group15.Webspresso.repository.OrderRepository;
import com.group15.Webspresso.service.CartService;
import com.group15.Webspresso.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
    
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    @PostMapping("/placeOrder")
    public String placeOrder(HttpServletRequest request, Model model) {
        // Get the order details from the request parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String postcode = request.getParameter("postcode");
        String cardNumber = request.getParameter("cardNumber");
        String expiration = request.getParameter("expiration");
        String cvv = request.getParameter("cvv");

        // Get the cart items from the session
        HttpSession session = request.getSession();
        // List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        Cart cart = cartService.getCurrentCart(request.getSession());
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        // Create a new order and add order items for each cart item
        Order order = new Order();
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setAddress(address);
        order.setCity(city);
        order.setCounty(county);
        order.setPostcode(postcode);
        order.setCardNumber(cardNumber);
        order.setExpiryDate(expiration);
        order.setCvv(cvv);
        LocalDate date = LocalDate.now();
        order.setOrderDate(date);
        User user = userService.findByUsername("john");
        order.setUser(user);

        BigDecimal totalPrice = BigDecimal.ZERO;

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getProductPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            totalPrice = totalPrice
                    .add(BigDecimal.valueOf(cartItem.getProduct()
                            .getProductPrice()).multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);


        // Save the order to the database
        orderRepository.save(order);

        // Clear the cart items from the session
        session.removeAttribute("cartItems");

        // Add the order to the model
        model.addAttribute("order", order);

        // Return the view for the order confirmation
        return "redirect:/order-confirmation";
    }

}
