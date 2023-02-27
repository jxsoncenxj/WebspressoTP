package com.group15.Webspresso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.group15.Webspresso.entity.User;
import com.group15.Webspresso.service.UserService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class UserController {

    private UserService userService;
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

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return redirectString;
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
}
