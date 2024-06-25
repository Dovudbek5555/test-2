package com.example.expess24.repository;

import com.example.expess24.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "promo-code", collectionResourceRel = "list")
public interface PromoCodeRepository extends JpaRepository<PromoCode, Integer> {
}
