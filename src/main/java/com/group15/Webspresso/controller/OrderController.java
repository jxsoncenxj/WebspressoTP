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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group15.Webspresso.classes.OrderStatus;
import com.group15.Webspresso.entity.Cart;
import com.group15.Webspresso.entity.CartItem;
import com.group15.Webspresso.entity.Order;
import com.group15.Webspresso.entity.OrderItem;
import com.group15.Webspresso.entity.Product;
import com.group15.Webspresso.entity.User;
import com.group15.Webspresso.repository.CartItemRepository;
import com.group15.Webspresso.repository.OrderRepository;
import com.group15.Webspresso.service.CartService;
import com.group15.Webspresso.service.OrderService;
import com.group15.Webspresso.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
    
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

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
        order.setOrderStatus(OrderStatus.RECIEVED);
        LocalDate date = LocalDate.now();
        order.setOrderDate(date);

        Object userIdObj = session.getAttribute("userId");
        int userId = (userIdObj != null) ? (int) userIdObj : 0;

        User user = userService.getUserById(userId);
        order.setUser(user);

        BigDecimal totalPrice = BigDecimal.ZERO;

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            product.setProductStock(product.getProductStock() - cartItem.getQuantity());
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

        //Clear the cart
        cart.clear();
        cartItemRepository.deleteAll();

        // Add the order to the model
        session.setAttribute("orderId", order.getId());

        // Return the view for the order confirmation
        return "order-confirmation";
    }


    @GetMapping("/orderConfirmation")
    public String showOrderConfirmation(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long orderId = (Long) session.getAttribute("orderId");
        Order order = orderService.getOrderByID(orderId);
        model.addAttribute("order", order);
        return "order-confirmation";
    }

    // handler method to display all orders
    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    //handler method to edit the order
    @GetMapping("/orders/edit/{id}")
    public String editOrderForm(@PathVariable Long id, Model model){
        model.addAttribute("order", orderService.getOrderByID(id));
        return "edit_order";
    }

    //post method to update the order
    @PostMapping("/orders/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute("order") Order order, Model model){
        Order ogOrder = orderService.getOrderByID(id);
        ogOrder.setOrderStatus(order.getOrderStatus());

        orderService.updateOrder(ogOrder);
        return "redirect:/orders";
    }

    //handler method to display detailed view of an order
    @GetMapping("/orders/summary/{id}")
    public String orderSummary(@PathVariable Long id, Model model){
        model.addAttribute("order", orderService.getOrderByID(id));
        return "order_summary";
    }
}   
