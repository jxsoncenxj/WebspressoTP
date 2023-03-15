package com.group15.Webspresso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    // handler method to handle list students and return model and view
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
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return redirectString;
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable int id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "edit_product";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute("product") Product product, Model model){
        Product ogProduct = productService.getProductById(id);
        ogProduct.setId(id);
        ogProduct.setProductName(product.getProductName());
        ogProduct.setProductPrice(product.getProductPrice());
        ogProduct.setProductDescription(product.getProductDescription());
        ogProduct.setProductStock(product.getProductStock());

        productService.updateProduct(ogProduct);
        return redirectString;
    }

    @GetMapping("/products/{id}")
    public String deleteProduct(@PathVariable int id){
        productService.deleteProductById(id);
        return redirectString;
    }
}
