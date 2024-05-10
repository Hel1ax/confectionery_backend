package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnoreProperties({"password", "role", "email"})
    private User user;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderItem> orderItems;
    @Column
    private double totalPrice;
    private String deliveryAddress;
}