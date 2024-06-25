package com.example.expess24.repository;

import com.example.expess24.entity.Basket;
import com.example.expess24.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    Basket findByOwner(User user);
}
