package com.group15.Webspresso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.group15.Webspresso.entity.Order;
import com.group15.Webspresso.entity.User;
import com.group15.Webspresso.repository.UserRepository;
import com.group15.Webspresso.service.OrderService;
import com.group15.Webspresso.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class UserController {

    private UserService userService;
    @Autowired
    private OrderService orderService;
    private String redirectString = "redirect:/users";

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    //handler method to display all users
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
    
    @GetMapping("/users/new")
    public String createUserForm(Model model) {
        // create product object to hold product form data
        User user = new User();
        model.addAttribute("user", user);
        return "create_user";
    }

    @GetMapping("/signup")
    public String signUpUserForm(Model model) {
        // create product object to hold product form data
        User user = new User();
        model.addAttribute("user", user);
        return "sign-up";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return redirectString;
    }

    @PostMapping("/signup")
    public String saveUserForSignUp(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "login";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit_user";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user, Model model) {
        User ogUser = userService.getUserById(id);
        ogUser.setId(id);
        ogUser.setEmail(user.getEmail());
        ogUser.setPassword(user.getPassword());

        userService.updateUser(ogUser);
        return redirectString;
    }

    @GetMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return redirectString;
    }

    @GetMapping("/userDashboard/{userId}")
    public String userDashboard(@PathVariable("userId") int userId, Model model, HttpSession session) {
        String sessionType = (String) session.getAttribute("sessionType");
        if(sessionType != null){
            User user = userService.getUserById(userId);
            List<Order> orders = orderService.getOrdersForUser(userId);
            // Add the user object to the model for use in the Thymeleaf template
            model.addAttribute("user", user);
            //Add the orders to the model for use in thethymeleaf template  
            model.addAttribute("orders", orders);
            // Return the name of the Thymeleaf template for the user dashboard page
            return "userDashboard";
        } else {
            return "redirect:/login2";
        }
    }

    @GetMapping("/userDashboard")
    public String userDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        int userId = (int) session.getAttribute("userId"); // Retrieve the user ID from the session
        // Use the user and user ID to display the appropriate information on the
        // dashboard
        // For example, you could pass the user and user ID to a service method to
        // retrieve data
        // and then add that data to the model for rendering in the dashboard view
        model.addAttribute("user", user);
        model.addAttribute("userId", userId);
        return "redirect:/userDashboard/" + userId;
    }

}
