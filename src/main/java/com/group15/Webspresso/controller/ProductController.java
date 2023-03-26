package com.group15.Webspresso.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.group15.Webspresso.entity.Product;
import com.group15.Webspresso.service.ProductService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class ProductController {
    
    private ProductService productService;
    private String redirectString = "redirect:/products";

    public ProductController(ProductService productService) {
        super();
        this.productService = productService;
    }

    // handler method to handle list students and return model and view
    @GetMapping("/products")
    public String listProducts(Model model, HttpSession session) {
        String sessionType = (String) session.getAttribute("sessionType");
        if(sessionType != null && sessionType.equals("admin")){
            model.addAttribute("products", productService.getAllProducts());
            return "products";
        } else {
            return "redirect:/adminLogin";
        }
    }

    // handler method to handle list products and return model and view
    @GetMapping("/productsPage")
    public String displayProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "productsPage";
    }

    @GetMapping("/products/new")
    public String createProductForm(Model model) {
        //create product object to hold product form data
        Product product = new Product();
        model.addAttribute("product", product);
        return "create_product";
    }

    @PostMapping("/products")
    public String addProduct(@ModelAttribute("product") Product product,
            @RequestParam("productImage") MultipartFile imageFile) {
        try {
            // Set the image data on the product object
            product.setImageData(imageFile.getBytes());

            // Save the product in the database
            productService.saveProduct(product);

            return "redirect:/products";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable int id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "edit_product";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute("product") Product product,
            @RequestParam("productImage") MultipartFile imageFile, Model model) {
        Product ogProduct = productService.getProductById(id);
        ogProduct.setId(id);
        ogProduct.setProductName(product.getProductName());
        ogProduct.setProductPrice(product.getProductPrice());
        ogProduct.setProductDescription(product.getProductDescription());
        ogProduct.setProductStock(product.getProductStock());

        try {
            // Set the image data on the product object
            ogProduct.setImageData(imageFile.getBytes());
            productService.updateProduct(ogProduct);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

        return redirectString;
    }

    @GetMapping("/products/{id}")
    public String deleteProduct(@PathVariable int id){
        productService.deleteProductById(id);
        return redirectString;
    }
}
