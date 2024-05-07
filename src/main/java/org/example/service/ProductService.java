package org.example.service;

import org.example.dao.ProductDao;
import org.example.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public Product getProductById(Long id) {
        return productDao.getProductById(id);
    }

    public List<Product> getProductsByName(String name) {
        return productDao.getProductsByName(name);
    }

    public List<Product> getProductsByCategory(String category) {
        return productDao.getProductsByCategory(category);
    }

    public Product addProduct(Product product) {
        return productDao.addProduct(product);
    }

    public Product updateProduct(Long id, Product product) {
        return productDao.updateProduct(id, product);
    }

    public void deleteProduct(Long id) {
        productDao.deleteProduct(id);
    }
}
