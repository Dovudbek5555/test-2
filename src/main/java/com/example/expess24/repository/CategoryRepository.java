package com.example.expess24.repository;

import com.example.expess24.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "category", collectionResourceRel = "list")
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
