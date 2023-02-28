package com.group15.Webspresso.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class MainController {

  @GetMapping(value = "/")
  public String index() {
    return "index";
  }

  @GetMapping(value = "/login2")
  public String login() {
    return "login";
  }

  @GetMapping(value = "/signup2")
  public String signup() {
    return "sign-up";
  }

  @GetMapping(value = "/adminLogin")
  public String adminlogin() {
    return "adminlogin";
  }

  @GetMapping("/home")
  public String home(Model model) {
    // Add model attributes as needed
    return "index";
  }

}
 