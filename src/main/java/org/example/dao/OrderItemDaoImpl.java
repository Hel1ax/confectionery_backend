package org.example.dao;

import org.example.entity.OrderItem;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderItem findById(Long id) {
        return entityManager.find(OrderItem.class, id);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        entityManager.persist(orderItem);
        return orderItem;
    }

    @Override
    public void delete(OrderItem orderItem) {
        entityManager.remove(orderItem);
    }
}

