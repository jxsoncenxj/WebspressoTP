package com.group15.Webspresso.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController extends AbstractErrorController {

  public CustomErrorController(ErrorAttributes errorAttributes) {
    super((org.springframework.boot.web.servlet.error.ErrorAttributes) errorAttributes);
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }

  @RequestMapping("/error")
  public String handleError(Model model, HttpServletRequest request) {
    // error handling logic
    return "error";
  }
}
