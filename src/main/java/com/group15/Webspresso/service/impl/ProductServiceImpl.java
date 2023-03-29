package com.group15.Webspresso.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.group15.Webspresso.entity.Product;
import com.group15.Webspresso.repository.ProductRepository;
import com.group15.Webspresso.service.ProductService;



@Service
public class ProductServiceImpl implements ProductService {
    
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        super();
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProducts(String searchText) {
        if (searchText != null && !searchText.isEmpty()) {
            return productRepository.findByProductNameContaining(searchText);
        } else {
            return productRepository.findAll();
        }
    }
}
