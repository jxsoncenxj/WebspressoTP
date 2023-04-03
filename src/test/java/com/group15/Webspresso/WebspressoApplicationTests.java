package com.group15.Webspresso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.ui.ExtendedModelMap;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import com.group15.Webspresso.controller.UserController;
import com.group15.Webspresso.entity.User;
import com.group15.Webspresso.repository.UserRepository;
import com.group15.Webspresso.service.UserService;
import com.group15.Webspresso.service.impl.UserServiceImpl;
import com.group15.Webspresso.entity.Order;
import com.group15.Webspresso.service.OrderService;
import com.group15.Webspresso.entity.*;
import com.group15.Webspresso.controller.*;
import com.group15.Webspresso.repository.*;
import org.springframework.security.core.Authentication;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class WebspressoApplicationTests {

    @Autowired
    private UserRepository userRepository;

    private final UserService userService = new UserServiceImpl(userRepository);

    private final UserController userController = new UserController(userService);

    @Test
    public void testCreateUserForm(){
        UserController controller = new UserController(userService);
        Model model = new BindingAwareModelMap();

        String viewName  = controller.createUserForm(model);

        assertEquals("create_user", viewName);
        assertEquals(new User(), model.getAttribute("user"));
    }

    @Test
    public void testSignUpUserForm() {
        Model model = new BindingAwareModelMap();
        String viewName = userController.signUpUserForm(model);
    
        assertEquals("sign-up", viewName);
    
        User user = (User) model.getAttribute("user");
        assertNotNull(user);
        assertNull(user.getId());
    }

    @Test
    public void testUpdateUser(){
        //Arrange
        //Create a test user
        User user = new User();
        user.setId(1);
        user.setEmail("test@testcode.com");
        user.setPassword("testpassword");
        
        //Act
        //Call the controller method
        String result = userController.updateUser(user.getId(), user, new ExtendedModelMap());

        //Assert
        //Verify that the mehtod returns the expected values
        assertEquals("redirect:/users", result);

        //Verify that the was updated in UserService
        User updateUser = userService.getUserById(1);
        assertEquals(user.getEmail(), updateUser.getEmail());
        assertEquals(user.getPassword(), updateUser.getPassword());
    }

    @Test
    public void testSaveUser(){
        //Arrange
        User user = new User();
        user.setId(1);
        user.setEmail("example@test");
        user.setPassword("testpassword");

        //Act
        String result = userController.saveUser(user);

        //Assert
        assertEquals("redirect:/users", result);

        //Verify that the user has been saved in UserService
        User savedUser = userService.getUserById(1);
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    public void testEditUserForm(){
        //Arrange
        User user = new User();
        user.setId(1);
        user.setEmail("john@exmaple.com");

        UserService userService = Mockito.mock(UserService.class);
        when(userService.getUserById(1)).thenReturn(user);

        //Create a Hashmap for model objects
        Model model = new ExtendedModelMap();

        //Act
        String viewName = userController.editUserForm(1, model);

        //Assert
        assertEquals("edit_user", viewName);
        assertEquals(user, model.getAttribute("user"));
    }
    @Test
    public void testDeleteUser() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setEmail("john@example.com");

        //Create a UserService Object 
        UserService userService = Mockito.mock(UserService.class);
        when(userService.getUserById(1)).thenReturn(user);

        // Act
        String viewName = userController.deleteUser(1);

        // Assert
        verify(userService, times(1)).deleteUserById(1);
        assertEquals("redirect:/users", viewName);
    }

    @Test
    public void testUserDashboard() {
        // Arrange
        int userId = 1;
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("sessionType")).thenReturn("user");
    
        Model model = mock(Model.class);
    
        UserService userService = mock(UserService.class);
        User user = new User();
        user.setId(userId);
        when(userService.getUserById(userId)).thenReturn(user);
    
        OrderService orderService = mock(OrderService.class);
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        when(orderService.getOrdersForUser(userId)).thenReturn(orders);
    
        UserController controller = new UserController();
        controller.setUserService(userService);
        controller.setOrderService(orderService);
    
        // Act
        String viewName = controller.userDashboard(userId, model, session);
    
        // Assert
        assertEquals("userDashboard", viewName);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("orders", orders);
    }

    @Test
    public void testAddToCart(){
        //Arrange
        CartController cartController = new CartController();
        ProductRepository productRepository = mock(ProductRepository.class);
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        Authentication authentication = mock(Authentication.class);

        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductDescription("This is a test product");
        product.setProductPrice(10.00);
        product.setProductStock(5);
        product.setId(1);
        //Act
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        int productId = 1;
        int quantity = 2;

        cartController.addToCart(productId, quantity, httpServletRequest, authentication, httpSession);
        //Assert
        verify(httpSession, times(1)).setAttribute(eq("cart"), any(Cart.class));
    }
 }
        






  








	


