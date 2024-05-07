package org.example.dao;

import org.example.entity.Order;
import org.example.entity.User;

import java.util.List;

public interface OrderDao {
    Order findById(Long id);
    Order save(Order order);
    void delete(Order order);
    List<Order> findByUser(User user);
}

