package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.entity.Order;
import org.example.entity.OrderItem;
import org.example.entity.User;
import org.example.service.AuthService;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> createOrder() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = authService.findByUsername(auth.getName());
        Order order = orderService.createOrder(user);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = authService.findByUsername(auth.getName());
            List<Order> orders = orderService.getAllOrdersForUser(user);
            return ResponseEntity.ok(orders);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<?> getOrderItemById(@PathVariable Long orderId, @PathVariable Long itemId) {
        OrderItem orderItem = orderService.getOrderItemById(orderId, itemId);
        return ResponseEntity.ok(orderItem);
    }

    @PostMapping("/{orderId}/items/{productId}")
    public ResponseEntity<?> addItemToOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        try{
            OrderItem orderItem = orderService.addItemToOrder(orderId, productId);
            return ResponseEntity.ok(orderItem);
        }catch (Exception e){
            System.out.println(e.getClass());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<?> updateItemQuantity(@PathVariable Long orderId, @PathVariable Long itemId, @RequestParam int quantity) {
        OrderItem orderItem = orderService.updateItemQuantity(orderId, itemId, quantity);
        return ResponseEntity.ok(orderItem);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<?> removeItemFromOrder(@PathVariable Long orderId, @PathVariable Long itemId) {
        OrderItem orderItem = orderService.removeItemFromOrder(orderId, itemId);
        return ResponseEntity.ok(orderItem);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e.getClass());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
