package com.example.expess24.repository;

import com.example.expess24.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "region", collectionResourceRel = "list")
public interface RegionRepository extends JpaRepository<Region, Integer> {
}
