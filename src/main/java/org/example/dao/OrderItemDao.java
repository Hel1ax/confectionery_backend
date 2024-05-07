package org.example.dao;

import org.example.entity.OrderItem;

public interface OrderItemDao {
    OrderItem findById(Long id);
    OrderItem save(OrderItem orderItem);
    void delete(OrderItem orderItem);
}
