package org.example.dao;

import org.example.entity.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByCategory(String category);

    Product addProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);
}

