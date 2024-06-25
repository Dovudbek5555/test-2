package com.example.expess24.repository;

import com.example.expess24.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
