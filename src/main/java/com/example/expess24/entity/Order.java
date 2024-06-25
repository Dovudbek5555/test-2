package com.example.expess24.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    private List<PreBasket> preBasket;

    @ManyToOne
    private PromoCode promoCode;

    @ManyToOne
    private PayType payType;

    @Column(nullable = false)
    private Double totalCost;

    @ManyToOne
    private User user;

    @ManyToOne
    private Contact contact;

    @CreatedDate
    private Date createdAt;
}
