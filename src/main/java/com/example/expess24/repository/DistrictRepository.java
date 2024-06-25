package com.example.expess24.repository;

import com.example.expess24.entity.District;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Integer> {

    boolean existsByNameAndRegionId(String name, Integer regionId);

}
