package com.group15.Webspresso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group15.Webspresso.entity.User;
import com.group15.Webspresso.repository.UserRepository;
import com.group15.Webspresso.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    // @GetMapping("/login")
    // public String showLoginForm() {
    //     return "login";
    // }

    // @PostMapping("/login")
    // public String login(@RequestParam String email, @RequestParam String password) {
    //     // Authenticate the user and redirect to the home page
    //     return "redirect:/home";
    // }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login2")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session,
            RedirectAttributes redirectAttributes, Model model) {
        User user = userService.findByUsername(email);
        if (user != null && userService.checkPassword(password, user.getPassword())) {
            session.setAttribute("user", user);
            int userId = user.getId(); // Get the ID of the authenticated user
            session.setAttribute("userId", userId); // Add the user ID to the session
            session.setAttribute("sessionType", "user");
            // if the user is trying to add a profuct to cart after logging in
            if (session.getAttribute("authenticatedCheckout") != null) {
                return "redirect:/productsPage";
            }
            return "redirect:/userDashboard/" + userId; // Redirect to the userDashboard page with the ID parameter
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    // @RequestMapping(value = "/login2", method = RequestMethod.POST)
    // public String login(@RequestParam("email") String username,
    //         @RequestParam("password") String password,
    //         Model model) {
    //     User user = userRepository.findByUsernameAndPassword(username, password);
    //     if (user != null) {
    //         int userId = user.getId(); // Get the ID of the authenticated user
    //         return "redirect:/userDashboard/" + userId; // Redirect to the userDashboard page with the ID parameter
    //     } else {
    //         model.addAttribute("error", "Invalid username or password");
    //         return "login";
    //     }
    // }

    @RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
    public String adminLogin(@RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (username.equals("admin") && password.equals("password")) {
            session.setAttribute("isAdmin", true);
            session.setAttribute("sessionType", "admin");
            return "redirect:/products";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "adminlogin";
        }
    }


}