package com.group15.Webspresso;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.group15.Webspresso.entity.Cart;
import com.group15.Webspresso.entity.CartItem;
import com.group15.Webspresso.entity.Checkout;
import com.group15.Webspresso.entity.Order;
import com.group15.Webspresso.entity.OrderItem;
import com.group15.Webspresso.entity.OrderStatus;
import com.group15.Webspresso.entity.Product;
import com.group15.Webspresso.entity.ProductImage;
import com.group15.Webspresso.entity.User;

@ExtendWith(MockitoExtension.class)                         
public class EntityTests {

    private CartItem cartItem;

    private Cart cart;

    private Product product;

    @Mock
    private CartItem cartItem;

    @Mock
    private Cart cart;

    @Mock
    private Product product;

    @Test
    //Test to empty the cart.
    public void testClear() {
        //Creates a new test Arraylist.
        cart = new Cart();
        cart.setCartItems(new Arraylist<>());
        //Add a test item to the cart.
        cart.getCartItems().add(cartItem);
        //Clears the cart.
        cart.clear();
        //Checks that the cart is empty.
        assertEquals(0, cart.getCartItems().size());
    }

    @Test public void testGetTotalPrice() {
        //Creates a new test cartItem and sets its quantity to 2.
    }

}

public class 