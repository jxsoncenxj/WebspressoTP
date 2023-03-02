package com.group15.Webspresso;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.group15.Webspresso.entity.Order;
import com.group15.Webspresso.entity.OrderItem;
import com.group15.Webspresso.entity.Product;
import com.group15.Webspresso.entity.User;
import com.group15.Webspresso.repository.OrderRepository;
import com.group15.Webspresso.repository.ProductRepository;
import com.group15.Webspresso.service.OrderService;
import com.group15.Webspresso.service.ProductService;
import com.group15.Webspresso.service.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class WebspressoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WebspressoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
