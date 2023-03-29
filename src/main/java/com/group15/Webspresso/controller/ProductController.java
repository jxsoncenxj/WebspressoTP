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
    public String listProducts(Model model, HttpSession session) {
        String sessionType = (String) session.getAttribute("sessionType");
        if(sessionType != null && sessionType.equals("admin")){
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("imgUtil", new ImageUtil());
            return "products";
        } else {
            return "redirect:/adminLogin";
        }
    }

    // handler method to handle list products and return model and view
    @GetMapping("/productsPage")
    public String displayProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("imgUtil", new ImageUtil());
        return "productsPage";
    }

    //handeler method to display individual product page
    @GetMapping("/product/{id}")
    public String displayProducts(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("imgUtil", new ImageUtil());
        return "product.html";
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

    @GetMapping("/report")
    public void generateReport(HttpServletResponse response) throws JRException, IOException {
        // Load the report template from file
        File reportFile = ResourceUtils.getFile("classpath:product_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportFile.getAbsolutePath());

        // Create a data source and fill the report template with data
        List<Product> productList = productService.getAllProducts();
        JRDataSource dataSource = new ProductReportDataSource(productList);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export the report to PDF and send it as a response to the client
        byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        response.setContentType("application/pdf");
        response.setContentLength(reportBytes.length);
        response.setHeader("Content-Disposition", "inline; filename=product_report.pdf");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(reportBytes);
        outputStream.flush();
        outputStream.close();
    }

}
