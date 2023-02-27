package com.group15.Webspresso.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping(value = "/")
  public String home() {
    return "home.html";
  }

  @GetMapping(value = "/login")
  public String login() {
    return "login";
  }

  @GetMapping(value = "/signup")
  public String signup() {
    return "sign-up";
  }

  @GetMapping(value = "/adminlogin")
  public String adminlogin() {
    return "adminlogin";
  }
}
