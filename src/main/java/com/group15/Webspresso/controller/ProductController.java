package com.group15.Webspresso.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.group15.Webspresso.classes.ImageUtil;
import com.group15.Webspresso.classes.ProductReportDataSource;
import com.group15.Webspresso.entity.Product;
import com.group15.Webspresso.service.ProductService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

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
    public String listProducts(Model model, HttpSession session, @RequestParam(value = "searchText", required = false) String searchText) {
        String sessionType = (String) session.getAttribute("sessionType");
        if(sessionType != null && sessionType.equals("admin")){
            List<Product> products = productService.getProducts(searchText);
            model.addAttribute("products", products);
            model.addAttribute("imgUtil", new ImageUtil());
            return "products";
        } else {
            return "redirect:/adminLogin";
        }
    }

    // handler method to handle list products and return model and view
    @GetMapping("/productsPage")
    public String displayProducts(@RequestParam(value = "origin", required = false) String origin, Model model) {
        List<Product> products = productService.getAllProducts();
        if (origin != null && !origin.isEmpty()) {
            products = productService.getProductsByOrigin(origin);
        }
        model.addAttribute("products", products);
        model.addAttribute("imgUtil", new ImageUtil());
        return "productsPage";
    }
    

    //handeler method to display individual product page
    @GetMapping("/product/{id}")
    public String displayProducts(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("imgUtil", new ImageUtil());
        return "product";
    }

    @GetMapping("/product/origin/{origin}")
    public String displayProductByOrigin(@PathVariable String origin, Model model) {
        origin = origin.toUpperCase();
        model.addAttribute("products", productService.getProductsByOrigin(origin));
        model.addAttribute("imgUtil", new ImageUtil());
        return "productsByOriginPage";
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
    public String editProductForm(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "edit_product";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute("product") Product updatedProduct,
            @RequestParam("productImage") MultipartFile imageFile, Model model) throws IOException {
        Product originalProduct = productService.getProductById(id);
        originalProduct.setId(id);
        // Set the updated information on the existing product
        originalProduct.setProductName(updatedProduct.getProductName());
        originalProduct.setProductPrice(updatedProduct.getProductPrice());
        originalProduct.setProductDescription(updatedProduct.getProductDescription());
        originalProduct.setProductStock(updatedProduct.getProductStock());
        originalProduct.setOrigin(updatedProduct.getOrigin());
        originalProduct.setImageData(imageFile.getBytes());

        productService.updateProduct(originalProduct);
        return redirectString;
    }
    

    @GetMapping("/products/{id}")
    public String deleteProduct(@PathVariable int id){
        productService.deleteProductById(id);
        return redirectString;
    }


}
