package org.example.dao;

import org.example.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> getAllProducts() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
    public Product getProductById(Long id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return entityManager.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return entityManager.createQuery("SELECT p FROM Product p WHERE p.category = :category", Product.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public Product addProduct(Product product) {
        entityManager.persist(product);
        return product;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            product.setId(id);
            entityManager.merge(product);
        }
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        if (product != null) {
            entityManager.remove(product);
        }
    }
}
