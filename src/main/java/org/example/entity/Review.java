package org.example.entity;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rating;
    private String comment;
    @ManyToOne
    @JsonIgnoreProperties({"description", "price", "category"})
    private Product product;
    @ManyToOne
    @JsonIgnoreProperties({"password", "role", "email"})
    private User user;
}