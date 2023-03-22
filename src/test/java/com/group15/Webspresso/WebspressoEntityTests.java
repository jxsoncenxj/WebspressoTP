package com.group15.Webspresso;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.group15.Webspresso.entity.Cart;
import com.group15.Webspresso.entity.CartItem;
import com.group15.Webspresso.entity.Checkout;
import com.group15.Webspresso.entity.Order;
import com.group15.Webspresso.entity.OrderItem;
import com.group15.Webspresso.entity.Product;
import com.group15.Webspresso.entity.ProductImage;
import com.group15.Webspresso.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class EntityTests {

    @InjectMocks
    private OrderItem orderItem;

    @InjectMocks
    private User user;

    @InjectMocks
    private OrderStatus orderStatus;

    @InjectMocks
    private Checkout checkout;

    @InjectMocks
    private CartItem cartItem;

    @InjectMocks
    private Cart cart;

    @InjectMocks
    private Order order;

    @Mock
    private Product product;

    @Test
    //Test to empty the cart.
    public void testClearCart() {
        //Creates a test cartItem and an Arraylist.
        CartItem cartItem = new CartItem();

        cart.setCartItems(new Arraylist<>());
        //Add a test item to the cart.
        cart.getCartItems().add(cartItem);
        //Clears the cart.
        cart.clear();
        //Checks that the cart is empty.
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    //Test to see if the GetTotalPrice method works correctly.
    public void testGetTotalPrice() {
        //Creates a new test cartItem and sets its quantity to 5.
        Product product = new Product();
        cartItem.setQuantity(5);
        //Sets the price of the cartItem to 8.
        when(product.getProductPrice()).thenReturn(8.00);
        //The expected total price of the items is 40, since 5 * 8 = 40.
        double expectedTotalPrice = 40.00;
        double actualTotalPrice = cartItem.getTotalPrice();

        assertEquals(expectedTotalPrice, actualTotalPrice, 0.0);
    }

    @Test
    //Test to see if the getters and setters from Checkout.java work correctly
    public void testCheckoutGettersAndSetters() {
        //Assigning values to items.
        int expectedUserId = 123;
        int expectedCartId = 321L;
        BigDecimal expectedTotalPrice = new BigDecimal("50.00");
        LocalDateTime expectedCreatedAt = LocalDateTime.now;
        //Testing setters using the previously assigned values.
        Checkout checkout = new Checkout();
        checkout.setUserId(expectedUserId);
        checkout.setcartId(expectedCartId);
        checkout.settotalPrice(expectedTotalPrice);
        checkout.setcreatedAt(expectedCreatedAt);
        //Testing getters using the previously assigned values.
        assertEquals(expectedUserId, checkout.getUserId());
        assertEquals(expectedCartId, checkout.getCartId());
        assertEquals(expectedTotalPrice, checkout.getTotalPrice());
        assertEquals(expectedCreatedAt, checkout.getCreatedAt());
}

    @Test
    public void testSetOrderItems() {
        //Creates a new ArrayList and adds the OrderItem to it.
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        //Runs the method setOrderItems.
        order.setOrderItems(orderItems);
        //Verifies that the items have been set and the command works properly.
        verify(order).setOrderItems(orderItems);
    }

    @Test
    //Test to see if the getters and setters from OrderItem.java work correctly
    public void testOrderItemGettersAndSetters() {
        //Assigning values to items.
        Order expectedOrder = new Order("999");
        Product expectedProduct = new Product ("666");
        Integer expectedquantity = new Integer ("50.00");
        //Testing setters using the previously assigned values.
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(expectedOrder);
        orderItem.setProduct(expectedProduct);
        orderItem.setQuantity(expectedQuantity);
        //Testing getters using the previously assigned values.
        assertEquals(expectedOrder, orderItem.getOrder());
        assertEquals(expectedProduct, orderItem.getProduct());
        assertEquals(expectedQuantity, orderItem.getQuantity;
}

    @Test
    //Test to see if the getters and setters from Product.java work correctly
    public void testProductGettersAndSetters() {
        //Assigning values to items.
        int expectedId = 123;
        String expectedProductName = ("testname");
        String expectedProductDescription = ("testdescription");
        Double expectedProductPrice = 10.00;
        int expectedProductStock = 15;
        //Testing setters using the previously assigned values.
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(expectedId);
        orderStatus.setProductName(expectedProductName);
        orderStatus.setProductDescription(expectedProductDescription);
        orderStatus.setProductQuantity(expectedProductQuantity);

        //Testing getters using the previously assigned values.
        assertEquals(expectedId, orderStatus.getId());
        assertEquals(expectedProductName, orderStatus.getProductName());
        assertEquals(expectedProductDescription, orderStatus.getProductDescription());
        assertEquals(expectedProductQuantity, orderStatus.getProductQuantity());
    }

    @Test
    public void testUserDetailsMethods() {
        // Create mock data.
        String mockEmail = "testemail@mockito.com";
        String mockPassword = "testpassword";
        // Create User object and set its properties.
        User user = new User();
        user.setEmail(mockEmail);
        user.setPassword(mockPassword);
        // Use Mockito to verify that the UserDetails methods behave correctly.
        assertTrue(user.isEnabled());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertEquals(mockEmail, user.getUsername());
        assertThrows(UnsupportedOperationException.class, user::getAuthorities);
    }
}