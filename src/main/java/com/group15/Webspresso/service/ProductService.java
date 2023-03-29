package com.group15.Webspresso.service;

import java.util.List;

import com.group15.Webspresso.entity.Product;

public interface ProductService {
    
    List<Product> getAllProducts();

    Product saveProduct(Product product);

    Product getProductById(int id);
    
    Product updateProduct(Product product);

    void deleteProductById(int id);

    List<Product> getProducts(String searchText);
}
