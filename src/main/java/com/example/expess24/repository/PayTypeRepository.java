package com.example.expess24.repository;

import com.example.expess24.entity.PayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pay-type", collectionResourceRel = "list")
public interface PayTypeRepository extends JpaRepository<PayType, Integer> {
}
