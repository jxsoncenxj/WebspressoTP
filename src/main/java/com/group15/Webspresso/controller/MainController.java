package com.group15.Webspresso.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
public class MainController {

  @GetMapping(value = "/")
  public String index() {
    return "index";
  }

  @GetMapping(value = "/login2")
  public String login(HttpSession session) {
    //user is already logged in
    if (session.getAttribute("user") != null) {
      // User is already logged in, redirect to dashboard
      return "redirect:/userDashboard";
    } else {
      // User is not logged in, show login form
      return "login";
    }
  }

  @GetMapping("/logout2")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login2";
  }

  @GetMapping("/adminLogout")
  public String adminLogout(HttpSession session) {
    session.invalidate();
    return "redirect:/adminLogin";
  }

  @GetMapping(value = "/signup2")
  public String signup() {
    return "sign-up";
  }

  @GetMapping(value = "/adminLogin")
  public String adminlogin(HttpSession session) {
    if (session.getAttribute("isAdmin") != null) {
      // User is already logged in, redirect to dashboard
      return "redirect:/products";
    } else {
      // User is not logged in, show login form
      return "adminLogin";
    }
  }

  @GetMapping(value = "/about")
  public String about() {
    return "about";
  }

  @GetMapping("/home")
  public String home(Model model) {
    // Add model attributes as needed
    return "index";
  }

  @GetMapping(value = "/store")
  public String store() {
    return "productpage";
  }

  @GetMapping(value = "/aboutus")
  public String aboutus() {
    return "aboutus";
  }


}
 