package com.example.expess24.payload;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Integer id;

    private String name;

    private String description;

    private Integer categoryId;

    private Double price;

    private Integer measureId;

    private Integer restaurantId;

    private Boolean available;

}
