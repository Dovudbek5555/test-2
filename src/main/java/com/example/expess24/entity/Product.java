package com.example.expess24.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    private Measure measure;

    @ManyToOne
    private Restaurant restaurant;

    @Column(nullable = false)
    private Boolean available;

}
