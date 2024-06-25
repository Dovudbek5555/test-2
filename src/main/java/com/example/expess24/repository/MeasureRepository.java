package com.example.expess24.repository;

import com.example.expess24.entity.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "measure", collectionResourceRel = "list")
public interface MeasureRepository extends JpaRepository<Measure, Integer> {
}
