package org.example.service;

import org.example.dao.OrderDao;
import org.example.dao.OrderItemDao;
import org.example.entity.Order;
import org.example.entity.OrderItem;
import org.example.entity.Product;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final ProductService productService;
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;

    @Autowired
    public OrderService(OrderDao orderDao, OrderItemDao orderItemDao, ProductService productService) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.productService = productService;
    }
    @Transactional
    public Order createOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        return orderDao.save(order);
    }
    @Transactional
    public OrderItem addItemToOrder(Long orderId, Long productId) {

        Product product = productService.getProductById(productId);

        Order order = orderDao.findById(orderId);

        if (order == null){
            throw new IllegalArgumentException("Order not found with id" + orderId);
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setPrice(orderItem.getQuantity() * product.getPrice());

        orderItem = orderItemDao.save(orderItem);

        order.getOrderItems().add(orderItem);

        order.setTotalPrice(order.getTotalPrice() + orderItem.getPrice());

        orderDao.save(order);

        return orderItem;
    }

    @Transactional
    public OrderItem updateItemQuantity(Long orderId, Long itemId, int quantity) {
        Order order = orderDao.findById(orderId);
        if (order == null){
            throw new IllegalArgumentException("Order not found with id" + orderId);
        }
        OrderItem orderItem = orderItemDao.findById(itemId);
        if (orderItem == null){
            throw new IllegalArgumentException("OrderItem not found with id" + orderItem);
        }
        orderItem.setQuantity(quantity);
        orderItem.setPrice(orderItem.getQuantity() * orderItem.getProduct().getPrice());

        double totalPrice = order.getOrderItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
        order.setTotalPrice(totalPrice);
        orderDao.save(order);

        return orderItemDao.save(orderItem);
    }

    @Transactional
    public OrderItem removeItemFromOrder(Long orderId, Long itemId) {
        Order order = orderDao.findById(orderId);
        if (order == null){
            throw new IllegalArgumentException("Order not found with id" + orderId);
        }
        OrderItem orderItem = orderItemDao.findById(itemId);
        if (orderItem == null){
            throw new IllegalArgumentException("OrderItem not found with id" + orderItem);
        }
        order.getOrderItems().remove(orderItem);
        orderItemDao.delete(orderItem);

        double totalPrice = order.getOrderItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
        order.setTotalPrice(totalPrice);

        orderDao.save(order);
        return orderItem;
    }

    public void deleteOrder(Long orderId) {
        Order order = orderDao.findById(orderId);
        if (order != null) {
            orderDao.delete(order);
        }
    }

    public List<Order> getAllOrdersForUser(User user) {
        return orderDao.findByUser(user);
    }

    public Order getOrderById(Long orderId) {
        return orderDao.findById(orderId);
    }

    @Transactional
    public OrderItem getOrderItemById(Long orderId, Long itemId) {
        Order order = getOrderById(orderId);
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getId().equals(itemId)) {
                return orderItem;
            }
        }
        throw new IllegalArgumentException("Order item not found in the specified order.");
    }
}

